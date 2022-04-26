package ru.itis.backend.security.filters;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.backend.security.authentications.TokenAuthentication;
import ru.itis.backend.services.JwtBlacklistService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtBlacklistService service;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("JWT");

        if (token!=null){

            if (service.exists(token)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }

        chain.doFilter(request, response);
    }

}
