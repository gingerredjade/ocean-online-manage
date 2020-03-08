package com.ocean.conf;

import com.alibaba.fastjson.JSONObject;
import com.ocean.model.vo.ResponseVO;
import com.ocean.util.HttpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class InterceptorConfig implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        JSONObject content =  HttpUtil.getPostBodyByJson(request);
        if (content == null || content.keySet().size() == 0) {
            response.reset();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(JSONObject.toJSONString(ResponseVO.fail("请检查是否传参！")));
            pw.flush();
            pw.close();
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
