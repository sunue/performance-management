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
        Object obj = session.getAttribute("id");
        System.out.println((Long) obj);
        if (obj != null || requestUri.equals("/login.html") || requestUri.equals("/index.html")
            || requestUri.equals("/gateway_admin/user/login") || requestUri.equals("/")
            || requestUri.equals("/adminLogin.html") || requestUri.equals("")
            || requestUri.contains("administratorLogin") || requestUri.contains("teacherLogin")
            || requestUri.equals("/gateway_admin/user/logout") || requestUri.contains(".css")
            || requestUri.contains(".js") || requestUri.contains(".jpg")
            || requestUri.contains(".png") || requestUri.contains(".eot")
            || requestUri.contains(".svg") || requestUri.contains(".ttf")
            || requestUri.contains(".woff")) {
            chain.doFilter(request, response);
            return;
        }
        //resp.sendRedirect("/1.html");
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    @Override
    public void destroy() {
        return;
    }
}