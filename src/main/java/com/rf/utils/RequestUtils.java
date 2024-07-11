package com.rf.utils;

import com.rf.redis.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    private static final String LOCALHOST_IPV4 = "127.0.0.1";

    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";


    public static String getRemoteIp(){
        HttpServletRequest servletRequest =
                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteAddr = servletRequest.getRemoteAddr();
        if (StringUtils.isEmpty(remoteAddr) || remoteAddr.equals(RequestUtils.LOCALHOST_IPV6) || remoteAddr.equals(RequestUtils.LOCALHOST_IPV4)) {
            remoteAddr = servletRequest.getHeader("Cf-Connecting-Ip");
        }
        return remoteAddr;
    }





}
