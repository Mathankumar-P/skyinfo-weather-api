package com.skyinfo.weatherforecast;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtility {
    public static String getIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORMATTED=FOR");
        if(ip==null || ip.isEmpty()){
            ip=request.getRemoteAddr();
        }
        return ip;
    }
}
