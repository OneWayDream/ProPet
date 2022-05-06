package ru.itis.jwtserver.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.jwtserver.dto.AccessTokenResponse;
import ru.itis.jwtserver.dto.JwtModuleDto;
import ru.itis.jwtserver.dto.ModuleAuthorizationForm;
import ru.itis.jwtserver.dto.RefreshTokenResponse;
import ru.itis.jwtserver.exceptions.*;
import ru.itis.jwtserver.entities.JwtState;

import java.util.Date;

@Service
public class ModuleLoginServiceImpl implements ModuleLoginService {

    protected final JwtModuleService service;
    protected final PasswordEncoder passwordEncoder;

    protected String refreshSecretKey;
    protected String accessSecretKey;
    protected Long refreshTokenLifetime;
    protected Long accessTokenLifetime;

    @Autowired
    public ModuleLoginServiceImpl(
            JwtModuleService service,
            PasswordEncoder passwordEncoder,
            @Value("${jwt.module.refresh-token.secret-key}") String refreshSecretKey,
            @Value("${jwt.module.access-token.secret-key}") String accessSecretKey,
            @Value("${jwt.module.refresh-token.lifetime}") Long refreshTokenLifetime,
            @Value("${jwt.module.access-token.lifetime}") Long accessTokenLifetime
    ){
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.refreshSecretKey = refreshSecretKey;
        this.accessSecretKey = accessSecretKey;
        this.refreshTokenLifetime = refreshTokenLifetime;
        this.accessTokenLifetime = accessTokenLifetime;
    }

    @Override
    public RefreshTokenResponse login(ModuleAuthorizationForm form) {
        JwtModuleDto module;
        try{
            module = service.findByLogin(form.getLogin());
        } catch (EntityNotFoundException ex){
            throw new IncorrectUserDataException(ex);
        }
        if (module.getState().equals(JwtState.BANNED)){
            throw new BannedUserException();
        }
        if (passwordEncoder.matches(form.getPassword(), module.getHashPassword())){
            Date date = null;
            if (refreshTokenLifetime > 0){
                date = java.util.Calendar.getInstance().getTime();
                date.setTime(date.getTime() + refreshTokenLifetime);
            }
            String token = JWT.create()
                    .withSubject(module.getId().toString())
                    .withClaim("id", module.getId())
                    .withClaim("login", module.getLogin())
                    .withClaim("state", module.getState().toString())
                    .withClaim("role", module.getRole().toString())
                    .withClaim("expiration", date)
                    .sign(Algorithm.HMAC256(refreshSecretKey));
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
                    .withClaim("id", decodedJWT.getClaim("id").asLong())
                    .withClaim("login", decodedJWT.getClaim("login").asString())
                    .withClaim("role", decodedJWT.getClaim("role").asString())
                    .withClaim("state", decodedJWT.getClaim("state").asString())
                    .withClaim("expiration", date)
                    .sign(Algorithm.HMAC256(accessSecretKey));
            return AccessTokenResponse.builder()
                    .token(accessToken)
                    .expiredTime((date == null) ? -1 : date.getTime())
                    .build();
        } catch (JWTVerificationException ex) {
            throw new IncorrectJwtException(ex);
        }
    }

}
