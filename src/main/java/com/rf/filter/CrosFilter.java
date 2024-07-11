package com.rf.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CrosFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String[] allowDomain = {"http://112.74.186.207:8080"};
        // httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://112.74.186.207:8080");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//      String[] allowDomain = {"http://127.0.0.1:8083"};
//      httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8083");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "*");
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
