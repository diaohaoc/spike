//package com.dh.spike.config;
//
//import com.dh.spike.domain.SpikeUser;
//import com.dh.spike.service.SpikeUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// *
// * 解析函数的参数，直接为参数赋值
// *
// * Create by DiaoHao on 2020/10/23 14:59
// */
//@Service
//public class UserArgumentResolver implements HandlerMethodArgumentResolver {
//
//    @Autowired
//    SpikeUserService userService;
//
//
//    @Override
//    public boolean supportsParameter(MethodParameter methodParameter) {
//        Class<?> clz = methodParameter.getParameterType();
//        return clz == SpikeUser.class;
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//
//        String paramToken = request.getParameter(SpikeUserService.COOKIE_NAME_TOKEN);
//        String cookieToken = getCookieValue(request);
//        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
//            return null;
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
//        return userService.getByToken(response, token);
//    }
//
//    private String getCookieValue(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.equals(SpikeUserService.COOKIE_NAME_TOKEN)) {
//                return cookie.getValue();
//            }
//        }
//        return null;
//    }
//}
