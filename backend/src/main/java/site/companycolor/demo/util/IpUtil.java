package site.companycolor.demo.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {

    public static String getClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}