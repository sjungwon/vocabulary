package domhwangcha.voca.web.auth;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(LoginMember.class);

        boolean hasUserType = UserSession.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if(request == null){
            throw new IllegalStateException("No HttpServletRequest");
        }

        HttpSession session = request.getSession(false);

        if(session == null){
            throw new IllegalStateException("세션 정보를 가져올 수 없습니다.");
        }

        UserSession user = (UserSession) session.getAttribute(SessionConst.USER_SESSION.name());

        if(user == null){
            throw new IllegalStateException("세션에 회원 정보가 없습니다.");
        }

        return user;
    }
}
