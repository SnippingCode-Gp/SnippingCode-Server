//package com.Controller.tmp;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class SimpleCORSFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {}
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse resp,
//                         FilterChain chain) throws IOException, ServletException {
//        // TODO Auto-generated method stub
//        System.out.println("get Here");
//        HttpServletResponse response=(HttpServletResponse) resp;
////        response.setHeader("Access-Control-Allow-Origin", "http://localhost:9000");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//
//        chain.doFilter(req, resp);
//    }
//
//    @Override
//    public void destroy() {}
//
//}