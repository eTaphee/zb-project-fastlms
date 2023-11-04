package com.zerobase.fastlms.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String X_FORWARDED_FOR_HEADER = "X-FORWARDED-FOR";

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(USER_AGENT_HEADER);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR_HEADER);
        return (ip == null) ? request.getRemoteAddr() : ip;
    }
}
