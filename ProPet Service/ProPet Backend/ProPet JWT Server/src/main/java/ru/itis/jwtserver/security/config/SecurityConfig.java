package ru.itis.jwtserver.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import ru.itis.jwtserver.exceptions.handlers.ExceptionHandlerFilter;
import ru.itis.jwtserver.security.filters.JwtCheckingFilter;
import ru.itis.jwtserver.security.filters.JwtLogoutFilter;
import ru.itis.jwtserver.security.filters.TokenAuthenticationFilter;
import ru.itis.jwtserver.security.providers.TokenAuthenticationProvider;

import java.util.List;

@EnableWebSecurity()
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@Order(1)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected final PasswordEncoder passwordEncoder;
    protected final JwtCheckingFilter jwtCheckingFilter;
    protected final JwtLogoutFilter jwtLogoutFilter;
    protected final TokenAuthenticationFilter tokenAuthenticationFilter;
    protected final UserDetailsService userDetailsService;
    protected final TokenAuthenticationProvider tokenAuthenticationProvider;
    protected final ExceptionHandlerFilter exceptionHandlerFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().disable();
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtCheckingFilter, TokenAuthenticationFilter.class);
        http.addFilterBefore(jwtLogoutFilter, TokenAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, CorsFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

}
