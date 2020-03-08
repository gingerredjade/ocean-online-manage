package com.ocean.manager;

import com.alibaba.fastjson.JSON;
import com.ocean.model.vo.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    private  Logger log = LoggerFactory.getLogger(CustomAuthExceptionHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        setResponse(response);
        Throwable cause = authException.getCause();
        if (cause instanceof InvalidTokenException) {
            log.error("InvalidTokenException : {}",cause.getMessage());
            //Token无效
            response.getWriter().write(JSON.toJSONString(ResponseVO.fail("Token无效！")));
        } else {
            log.error("AuthenticationException : NoAuthentication");
            //资源未授权
            response.getWriter().write(JSON.toJSONString(ResponseVO.fail("未经过授权！")));
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setResponse(response);
        //访问资源的用户权限不足
        log.error("AccessDeniedException : {}",accessDeniedException.getMessage());
        response.getWriter().write(JSON.toJSONString(ResponseVO.fail("访问权限不足！")));
    }


    private void setResponse(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Cache-Control","no-cache");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");
    }
}