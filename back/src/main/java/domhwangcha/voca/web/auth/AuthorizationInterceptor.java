package domhwangcha.voca.web.auth;

import domhwangcha.voca.domain.Role;
import domhwangcha.voca.exception.ForbiddenException;
import domhwangcha.voca.exception.UnauthorizedException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod m = (HandlerMethod) handler;

        Authorization authorization = m.getMethodAnnotation(Authorization.class) != null ?
                m.getMethodAnnotation(Authorization.class) : m.getBean().getClass().getAnnotation(Authorization.class);

        if(authorization == null){
            return true;
        }

        if(authorization.allowGuest()){
            return true;
        }

        UserSession session = (UserSession) request.getSession().getAttribute(SessionConst.USER_SESSION.name());

        if(session == null){
            throw new UnauthorizedException();
        }

        List<Role> roles = Arrays.asList(authorization.roles());
        if(roles.size() > 0 && !roles.contains(session.getRole())){
            throw new ForbiddenException();
        }

        return true;
    }
}
