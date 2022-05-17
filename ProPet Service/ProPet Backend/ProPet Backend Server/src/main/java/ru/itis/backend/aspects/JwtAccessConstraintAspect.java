package ru.itis.backend.aspects;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import ru.itis.backend.annotations.JwtAccessConstraint;
import ru.itis.backend.exceptions.token.IncorrectJwtException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class JwtAccessConstraintAspect {

    protected List<String> secretKeys;

    @Autowired
    public JwtAccessConstraintAspect(
            @Value("${jwt.module.access-token.secret-key}") String moduleAccessSecretKey,
            @Value("${jwt.user.access-token.secret-key}") String userAccessSecretKey
    ){
        secretKeys = new ArrayList<>();
        secretKeys.add(moduleAccessSecretKey);
        secretKeys.add(userAccessSecretKey);
    }

    @Pointcut("@annotation(ru.itis.backend.annotations.JwtAccessConstraint)")
    public void aspectAnnotation(){}

    @Pointcut("execution(* *.*(..))")
    public void method(){}

    @Before("method() && aspectAnnotation()")
    public void handleAnnotations(JoinPoint joinPoint) throws Throwable{

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        JwtAccessConstraint accessConstraint = method.getAnnotation(JwtAccessConstraint.class);
        String token = (String) getParameterByName(joinPoint, accessConstraint.jwtArgName());

        if (accessConstraint.opRoles()){
            String[] opRoles = accessConstraint.opRolesArray();
            String role = (String) getJwtField(token, accessConstraint.jwtRoleFieldName(), String.class);
            if (Arrays.asList(opRoles).contains(role)){
                return;
            }
        }

        Object entity = getParameterByName(joinPoint, accessConstraint.argName());
        Object firstValue;
        if (accessConstraint.argField().equals("")){
            firstValue = entity;
        } else {
            Field field = entity.getClass().getDeclaredField(accessConstraint.argField());
            field.setAccessible(true);
            firstValue = field.get(entity);
        }
        Object secondValue = getJwtField(token, accessConstraint.jwtFieldName(), firstValue.getClass());

        if (!firstValue.equals(secondValue)){
            throw new AccessDeniedException("Access denied.");
        }
    }

    protected Object getParameterByName(JoinPoint proceedingJoinPoint, String parameterName) {
        MethodSignature methodSig = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        String[] parametersName = methodSig.getParameterNames();

        int idx = Arrays.asList(parametersName).indexOf(parameterName);

        if(idx != -1) {
            return args[idx];
        }
        return null;
    }

    protected Object getJwtField(String token, String field, Class<?> targetClass){
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
        return decodedJWT.getClaim(field).as(targetClass);
    }

}
