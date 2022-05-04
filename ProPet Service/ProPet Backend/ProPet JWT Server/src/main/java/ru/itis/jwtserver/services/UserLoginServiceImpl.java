package ru.itis.jwtserver.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.jwtserver.dto.*;
import ru.itis.jwtserver.exceptions.*;
import ru.itis.jwtserver.entities.JwtState;
import ru.itis.jwtserver.redis.services.RedisUsersService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    protected final JwtUserService service;
    protected final PasswordEncoder passwordEncoder;
    protected final RedisUsersService redisUsersService;
    protected final JwtBlacklistService jwtBlacklistService;

    protected String refreshSecretKey;
    protected String accessSecretKey;
    protected Long refreshTokenLifetime;
    protected Long accessTokenLifetime;

    @Autowired
    public UserLoginServiceImpl(
            JwtUserService service,
            PasswordEncoder passwordEncoder,
            RedisUsersService redisUsersService,
            JwtBlacklistService jwtBlacklistService,
            @Value("${jwt.user.refresh-token.secret-key}") String refreshSecretKey,
            @Value("${jwt.user.access-token.secret-key}") String accessSecretKey,
            @Value("${jwt.user.refresh-token.lifetime}") Long refreshTokenLifetime,
            @Value("${jwt.user.access-token.lifetime}") Long accessTokenLifetime
    ){
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.redisUsersService = redisUsersService;
        this.jwtBlacklistService = jwtBlacklistService;
        this.refreshTokenLifetime = refreshTokenLifetime;
        this.accessTokenLifetime = accessTokenLifetime;
        this.accessSecretKey = accessSecretKey;
        this.refreshSecretKey = refreshSecretKey;
    }

    @Override
    public RefreshTokenResponse login(UserAuthorizationForm form) {
        JwtUserDto user;
        try{
            if (form.getLogin() != null){
                user = service.findByLogin(form.getLogin());
            } else {
                user = service.findByMail(form.getMail());
            }
        } catch (EntityNotFoundException ex){
            throw new IncorrectUserDataException(ex);
        }
        if (user.getState().equals(JwtState.BANNED)){
            throw new BannedUserException();
        }
        if (passwordEncoder.matches(form.getPassword(), user.getHashPassword())){
            Date date = null;
            if (refreshTokenLifetime > 0){
                date = java.util.Calendar.getInstance().getTime();
                date.setTime(date.getTime() + refreshTokenLifetime);
            }
            String token = JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("id", user.getId())
                    .withClaim("login", user.getLogin())
                    .withClaim("state", user.getState().toString())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("expiration", date)
                    .withClaim("redis_id", user.getRedisId())
                    .sign(Algorithm.HMAC256(refreshSecretKey));
            redisUsersService.addTokenToUser(user, token);
            return RefreshTokenResponse.builder()
                    .token(token)
                    .expiredTime((date == null) ? -1 : date.getTime())
                    .build();
        } else {
            throw new IncorrectUserDataException();
        }
    }

    @Override
    public AccessTokenResponse authenticate(RefreshTokenResponse refreshTokenDto) {
        if (jwtBlacklistService.exists(refreshTokenDto.getToken())){
            throw new BannedTokenException();
        }
        try{
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(refreshSecretKey))
                    .build()
                    .verify(refreshTokenDto.getToken());
            Date date = java.util.Calendar.getInstance().getTime();
            if ((decodedJWT.getClaim("expiration").asDate() != null)
                    && (!date.before(decodedJWT.getClaim("expiration").asDate()))){
                throw new ExpiredJwtException();
            }
            if (accessTokenLifetime > 0){
                date.setTime(date.getTime() + accessTokenLifetime);
            } else {
                date = null;
            }
            String accessToken = JWT.create()
                    .withSubject(decodedJWT.getSubject())
                    .withClaim("id", decodedJWT.getClaim("id").asString())
                    .withClaim("login", decodedJWT.getClaim("login").asString())
                    .withClaim("role", decodedJWT.getClaim("role").asString())
                    .withClaim("state", decodedJWT.getClaim("state").asString())
                    .withClaim("expiration", date)
                    .sign(Algorithm.HMAC256(accessSecretKey));
            redisUsersService.addTokenToUser(JwtUserDto.builder()
                            .id(decodedJWT.getClaim("id").asLong())
                            .redisId(decodedJWT.getClaim("redis_id").asString())
                            .build(), accessToken);
            return AccessTokenResponse.builder()
                    .token(accessToken)
                    .expiredTime((date == null) ? -1 : date.getTime())
                    .build();
        } catch (JWTVerificationException ex) {
            throw new IncorrectJwtException(ex);
        }
    }

}
