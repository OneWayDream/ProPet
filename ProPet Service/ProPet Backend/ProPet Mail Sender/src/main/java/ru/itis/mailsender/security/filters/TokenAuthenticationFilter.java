package ru.itis.mailsender.security.filters;

import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.mailsender.security.authentications.TokenAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("JWT");

        if (token!=null){
            TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }

        chain.doFilter(request, response);
    }

}
