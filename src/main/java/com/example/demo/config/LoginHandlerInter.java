package com.example.demo.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登陆成功之后,有session
        Object object1=    request.getSession().getAttribute("users");
        if(object1==null){
                request.setAttribute("msg","请先登录");
                request.getRequestDispatcher("/login.html").forward(request,response);
                return false;
        }
        else {
            return true;
        }
    }
}
