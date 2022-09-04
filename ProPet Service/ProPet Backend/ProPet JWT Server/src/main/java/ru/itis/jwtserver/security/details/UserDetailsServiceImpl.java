package ru.itis.jwtserver.security.details;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.jwtserver.entities.AccessToken;
import ru.itis.jwtserver.exceptions.token.IncorrectJwtException;
import ru.itis.jwtserver.entities.JwtRole;
import ru.itis.jwtserver.entities.JwtState;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    List<String> secretKeys;

    @Autowired
    public UserDetailsServiceImpl(
            @Value("${jwt.module.access-token.secret-key}") String moduleAccessSecretKey,
            @Value("${jwt.user.access-token.secret-key}") String userAccessSecretKey
    ){
        this.secretKeys = new ArrayList<>();
        secretKeys.add(moduleAccessSecretKey);
        secretKeys.add(userAccessSecretKey);
    }

    @Override
    public UserDetails loadUserByUsername(String tokenValue) throws UsernameNotFoundException {
        DecodedJWT decodedJWT = null;
        for (String secretKey : secretKeys){
            try{
                decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                        .build()
                        .verify(tokenValue);
                break;
            } catch (JWTVerificationException ex){
                //next one
            }

        }
        if (decodedJWT == null){
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new IncorrectJwtException();
        }
        return new UserDetailsImpl(AccessToken.builder()
                .accountId(decodedJWT.getClaim("account_id").asLong())
                .login(decodedJWT.getClaim("role").asString())
                .role(decodedJWT.getClaim("role").as(JwtRole.class))
                .state(decodedJWT.getClaim("state").as(JwtState.class))
                .expiration(decodedJWT.getClaim("expiration").asDate())
                .build());
    }
}
