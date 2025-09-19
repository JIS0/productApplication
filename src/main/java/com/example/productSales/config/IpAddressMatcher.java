package com.example.productSales.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

public class IpAddressMatcher implements RequestMatcher {
    private static final Logger log = LoggerFactory.getLogger(IpAddressMatcher.class);

    private final List<String> allowedIps;

    public IpAddressMatcher(List<String> allowedIps) {
        this.allowedIps = allowedIps;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        log.warn("Incoming request from IP: {}", request.getRemoteAddr());
        return allowedIps.contains(clientIp);
    }
}
