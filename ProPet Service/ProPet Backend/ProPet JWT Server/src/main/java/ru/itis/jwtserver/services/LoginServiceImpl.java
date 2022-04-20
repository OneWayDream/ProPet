package ru.itis.jwtserver.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.jwtserver.dto.AccessTokenDto;
import ru.itis.jwtserver.dto.LoginPasswordDto;
import ru.itis.jwtserver.dto.RefreshTokenDto;
import ru.itis.jwtserver.exceptions.*;
import ru.itis.jwtserver.models.AccessUserState;
import ru.itis.jwtserver.models.DataAccessUser;
import ru.itis.jwtserver.redis.services.RedisUsersService;
import ru.itis.jwtserver.repositories.UsersRepository;

import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    protected final UsersRepository usersRepository;
    protected final PasswordEncoder passwordEncoder;
    private final RedisUsersService redisUsersService;
    protected final JwtBlacklistService jwtBlacklistService;

    protected String refreshSecretKey;

    @Value("${jwt.access-secret-key}")
    protected String accessSecretKey;

    @Value("${jwt.refresh-token-lifetime}")
    protected Long refreshTokenLifetime;

    @Value("${jwt.access-token-lifetime}")
    protected Long accessTokenLifetime;

    @Autowired
    public LoginServiceImpl(
            UsersRepository usersRepository,
            PasswordEncoder passwordEncoder,
            RedisUsersService redisUsersService,
            JwtBlacklistService jwtBlacklistService,
            @Value("${jwt.refresh-secret-key}") String refreshSecretKey,
            @Value("${jwt.access-secret-key}") String accessSecretKey,
            @Value("${jwt.refresh-token-lifetime}") Long refreshTokenLifetime,
            @Value("${jwt.access-token-lifetime}") Long accessTokenLifetime
    ){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisUsersService = redisUsersService;
        this.refreshSecretKey = refreshSecretKey;
        this.jwtBlacklistService = jwtBlacklistService;
        this.accessSecretKey = accessSecretKey;
        this.refreshTokenLifetime = refreshTokenLifetime;
        this.accessTokenLifetime = accessTokenLifetime;
    }

    @Override
    public RefreshTokenDto login(LoginPasswordDto loginPasswordDto) {
        DataAccessUser user = usersRepository.findByLogin(loginPasswordDto.getLogin()).orElseThrow(IncorrectUserDataException::new);
        if (user.getState().equals(AccessUserState.BANNED)){
            throw new BannedUserException();
        }
        if (passwordEncoder.matches(loginPasswordDto.getPassword(), user.getHashPassword())){
            Date date = java.util.Calendar.getInstance().getTime();
            date.setTime(date.getTime() + refreshTokenLifetime);
            String token = JWT.create()
                    .withSubject(user.getId().toString())
                    .withClaim("id", user.getId())
                    .withClaim("login", user.getLogin())
                    .withClaim("state", user.getState().toString())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("expiration", date)
                    .sign(Algorithm.HMAC256(refreshSecretKey));

            redisUsersService.addTokenToUser(user, token);


            return RefreshTokenDto.builder().token(token).expiredTime(date.getTime()).build();
        } else {
            throw new IncorrectUserDataException();
        }
    }

    @Override
    public AccessTokenDto authenticate(RefreshTokenDto refreshTokenDto) {
        if (jwtBlacklistService.exists(refreshTokenDto.getToken())){
            throw new BannedTokenException();
        }
        try{
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(refreshSecretKey))
                    .build()
                    .verify(refreshTokenDto.getToken());
            Date date = java.util.Calendar.getInstance().getTime();
            if (!date.before(decodedJWT.getClaim("expiration").asDate())){
                throw new ExpiredJwtException();
            }
            date.setTime(date.getTime() + accessTokenLifetime);
            String accessToken = JWT.create()
                    .withSubject(decodedJWT.getSubject())
                    .withClaim("login", decodedJWT.getClaim("login").asString())
                    .withClaim("role", decodedJWT.getClaim("role").asString())
                    .withClaim("state", decodedJWT.getClaim("state").asString())
                    .withClaim("expiration", date)
                    .sign(Algorithm.HMAC256(accessSecretKey));

            redisUsersService.addTokenToUser(usersRepository.findByLogin(decodedJWT.getClaim("login").asString())
                    .orElseThrow(IncorrectUserDataException::new), accessToken);

            return AccessTokenDto.builder()
                    .token(accessToken)
                    .expiredTime(decodedJWT.getClaim("expiration").asDate().getTime())
                    .build();
        } catch (JWTVerificationException ex) {
            throw new IncorrectJwtException(ex);
        }
    }

}
