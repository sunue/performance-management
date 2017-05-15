package com.performance.web.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        return;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestUri = ((HttpServletRequest) request).getServletPath();
        HttpSession session = req.getSession();
        Object administrator = session.getAttribute("administratorId");
        Object teacher = session.getAttribute("teacherId");
        //System.out.println("过滤器" + administrator);
        //System.out.println(teacher);
        if (administrator != null || teacher != null || requestUri.equals("/login.html")
            || requestUri.equals("/index.html") || requestUri.equals("/register.html")
            || requestUri.equals("/") || requestUri.equals("/adminLogin.html")
            || requestUri.equals("") || requestUri.contains("administratorLogin")
            || requestUri.contains("teacherLogin")
            || requestUri.equals("/gateway_admin/user/logout") || requestUri.contains(".css")
            || requestUri.contains(".js") || requestUri.contains(".jpg")
            || requestUri.contains(".png") || requestUri.contains(".eot")
            || requestUri.contains(".svg") || requestUri.contains(".ttf")
            || requestUri.contains(".woff")) {
            chain.doFilter(request, response);
            return;
        }
        resp.sendRedirect("/login.html"); //重定向，url改变
        //req.getRequestDispatcher("/index.html").forward(req, resp); //转发
    }

    @Override
    public void destroy() {
        return;
    }
}