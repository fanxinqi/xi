package com.xyb.interceptor;

import com.xyb.annotation.LoginRequired;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.exception.MyException;
import com.xyb.service.AccountInfoService;
import com.xyb.utils.JWTUtil;
import com.xyb.utils.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.xyb.constants.Constants.HEADER_TOKEN;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private AccountInfoService accountInfoService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            String accessToken = request.getHeader(HEADER_TOKEN);
            accessToken = StringUtils.isBlank(accessToken) ? request.getParameter(HEADER_TOKEN) : accessToken;
            if (StringUtils.isBlank(accessToken)) {
                throw new MyException("无token，请重新登录");
            }
            String userName = null;
            try {
                userName = JWTUtil.getUsername(accessToken);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException("用户不存在，请重新登录");
            }
            AccountInfoEntity user = accountInfoService.findByName(userName);
            if (user == null) {
                throw new MyException("用户不存在，请重新登录");
            }
//             当前登录用户@CurrentUser
//            request.setAttribute(CurrentUserConstants.CURRENT_USER, user);
            return true;
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
