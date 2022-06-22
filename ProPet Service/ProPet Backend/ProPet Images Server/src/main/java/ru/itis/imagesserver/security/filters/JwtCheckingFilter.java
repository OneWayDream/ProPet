package ru.itis.imagesserver.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.imagesserver.exceptions.token.BannedTokenException;
import ru.itis.imagesserver.exceptions.token.ExpiredJwtException;
import ru.itis.imagesserver.exceptions.token.IncorrectJwtException;
import ru.itis.imagesserver.services.JwtBlacklistService;

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
            @Value("${jwt.module.access-token.secret-key}") String moduleAccessSecretKey,
            @Value("${jwt.user.access-token.secret-key}") String userAccessSecretKey
    ){
        this.service = service;
        secretKeys = new ArrayList<>();
        secretKeys.add(moduleAccessSecretKey);
        secretKeys.add(userAccessSecretKey);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        if (request.getHeader("JWT") != null){
            String token = request.getHeader("JWT");
            if (service.exists(token)) {
                throw new BannedTokenException();
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
                throw new IncorrectJwtException();
            }

            if (decodedJWT.getClaim("expiration").asDate() != null){
                Date date = java.util.Calendar.getInstance().getTime();
                Date timeToDie = decodedJWT.getClaim("expiration").asDate();
                if (!date.before(timeToDie)){
                    throw new ExpiredJwtException();
                }
            }
        }
        chain.doFilter(request, response);
    }


}
