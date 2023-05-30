package domhwangcha.voca.web.auth;

import domhwangcha.voca.domain.Role;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
    Role[] roles() default {};
    boolean allowGuest() default false;
}
