package com.hanergy.out.config;

import com.hanergy.out.exceptionHandler.LoginException;
import com.hanergy.out.utils.CurrentUserConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginInterceptor  implements HandlerInterceptor {

    Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final String NOT_INTERCEPT_URI = "swagger";//不拦截的URI


    public final static String ACCESS_TOKEN = "access_token";

    //@Autowired
    //ISysUserService sysUserService;

    //@Autowired
    //ICdtCheckHistoryService cdtCheckHistoryService;

    // 在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        //不拦截swagger2在线文档
        if (uri.contains(NOT_INTERCEPT_URI) || uri.contains("/cs-web/v2/api-docs") || uri.contains("/common/sendEmail") || uri.contains("/user/changePassword")) {
            log.info("不拦截" + uri);
            return true;
        }

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断是否存在令牌信息，如果存在，则允许登录
        String accessToken = request.getHeader(ACCESS_TOKEN);

        if (null == accessToken) {
            throw new LoginException(401, "无token，请重新登录");
        } else {
            // 从Redis 中查看 token 是否过期
            Claims claims;
            /*
            try{
                SysUser sysUser = sysUserService.findByAccessToken(accessToken);
                if (sysUser == null) {
                    response.setStatus(401);
                    throw new LoginException(401, "用户不存在，请重新登录");
                }
                // 当前登录用户@CurrentUser
                request.setAttribute(CurrentUserConstants.CURRENT_USER, sysUser);
                //claims = TokenUtils.parseJWT(accessToken);
            }catch (ExpiredJwtException e){
                response.setStatus(401);
                throw new LoginException(401, "token失效，请重新登录");
            }catch (SignatureException se){
                response.setStatus(401);
                throw new LoginException(401, "token令牌错误");
            }
             */
            //String userName = claims.getId();
            // 获取过期时间
            /*
            Date expire = claims.getExpiration();
            Long minute = DateUtils.getMinuteDiff(new Date(),expire);
            if (minute < 10){
                String token = TokenUtils.createJwtToken(userName);
            }
             */
//            SysUser user = sysUserService.findByUserName(userName);
//            if (user == null) {
//                response.setStatus(401);
//                throw new LoginException(401, "用户不存在，请重新登录");
//            }
            // 当前登录用户@CurrentUser
            //request.setAttribute(CurrentUserConstants.CURRENT_USER, user);
            return true;
        }

    }
    // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    // 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
