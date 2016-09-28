package com.cci.projectx.core.interceptor;

import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.annotation.IgnoreAuth;
import com.google.common.cache.Cache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("sessionCache")
    private Cache<String, Long> sessionCache;

    private final static String AUTHORIZATION = "Authorization";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        boolean isIgnore = checkIgnore(handler);

        if (!isIgnore) {
//            Controller controller = handlerMethod.getBeanType().getAnnotation(Controller.class);
//            if (null != controller) {
//                HttpSession session = request.getSession();
//                Object user = session.getAttribute("user");
//                if (null == user) {
//                    response.sendRedirect("/login");
//                    return true;
//                }
//            }

            RestController restController = handlerMethod.getBeanType().getAnnotation(RestController.class);
            if (null != restController) {

                String authToken = request.getHeader(AUTHORIZATION);
                if (StringUtils.isEmpty(authToken)) {
                    HRErrorCode.throwBusinessException(HRErrorCode.UN_LOGIN);
                }

                Long userId = sessionCache.getIfPresent(authToken);
                if(null==userId){
                    HRErrorCode.throwBusinessException(HRErrorCode.UN_LOGIN);
                }

                request.setAttribute("userId", userId);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {

    }


    private boolean checkIgnore(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        IgnoreAuth ignoreAuth = handlerMethod.getMethodAnnotation(IgnoreAuth.class);
        if (null != ignoreAuth) {
            return true;
        }

        return false;
    }

}
