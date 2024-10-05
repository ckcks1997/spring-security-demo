package site.companycolor.demo.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {

    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = null;

        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            ip = forwardedFor.split(",")[0].trim();
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}