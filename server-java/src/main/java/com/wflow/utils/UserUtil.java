package com.wflow.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {
    
    public static String getLoginUserId() {
        // First try to get authenticated user from SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            return auth.getName();
        }
        
        // If no authenticated user, try to get from X-User-Id header
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String userId = request.getHeader("X-User-Id");
                if (userId != null && !userId.trim().isEmpty()) {
                    return userId;
                }
            }
        } catch (Exception e) {
            // Log error but continue with default
        }
        
        return "system"; // Default fallback value if no user found
    }
} 