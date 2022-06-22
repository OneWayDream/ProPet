package ru.itis.imagesserver.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtAccessConstraint {

    String jwtArgName() default "token";
    String jwtFieldName() default "";
    String argName() default "";
    String argField() default "";
    boolean opRoles() default false;
    String jwtRoleFieldName() default "";
    String[] opRolesArray() default "";

}
