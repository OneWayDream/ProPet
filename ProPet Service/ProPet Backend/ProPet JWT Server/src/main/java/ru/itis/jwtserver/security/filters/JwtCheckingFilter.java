package ru.itis.jwtserver.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.jwtserver.exceptions.ExpiredJwtException;
import ru.itis.jwtserver.exceptions.IncorrectJwtException;
import ru.itis.jwtserver.services.JwtBlacklistService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtCheckingFilter extends OncePerRequestFilter {

    protected final JwtBlacklistService service;
    protected List<String> secretKeys;

    @Autowired
    public JwtCheckingFilter(
            JwtBlacklistService service,
            @Value("${jwt.module.refresh-token.secret-key}") String moduleRefreshSecretKey,
            @Value("${jwt.module.access-token.secret-key}") String moduleAccessSecretKey,
            @Value("${jwt.user.refresh-token.secret-key}") String userRefreshSecretKey,
            @Value("${jwt.user.access-token.secret-key}") String userAccessSecretKey
    ){
        this.service = service;
        secretKeys = new ArrayList<>();
        secretKeys.add(moduleRefreshSecretKey);
        secretKeys.add(moduleAccessSecretKey);
        secretKeys.add(userRefreshSecretKey);
        secretKeys.add(userAccessSecretKey);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        List<String> tokens = new ArrayList<>();
        if (request.getHeader("JWT") != null){
            tokens.add(request.getHeader("JWT"));
        }
        if (request.getHeader("refresh-token") != null){
            tokens.add(request.getHeader("refresh-token"));
        }

        for (String token : tokens){
            if (service.exists(token)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            DecodedJWT decodedJWT = null;
            for (String secretKey : secretKeys){
                try{
                    decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                            .build()
                            .verify(token);
                    break;
                } catch (JWTVerificationException ex){
                    //wrong key :c
                }
            }

            if (decodedJWT == null){
                throw new IncorrectJwtException("This token is incorrect");
            }

            if (decodedJWT.getClaim("expiration").asDate() != null){
                Date date = java.util.Calendar.getInstance().getTime();
                Date timeToDie = decodedJWT.getClaim("expiration").asDate();
                if (!date.before(timeToDie)){
                    throw new ExpiredJwtException("The token is expired.");
                }
            }
        }
        chain.doFilter(request, response);
    }


}
