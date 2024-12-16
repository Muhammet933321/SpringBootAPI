package com.Blix.UnityAPI.loggers;

import com.Blix.UnityAPI.services.IPLogService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IpLoggingFilter implements Filter {

    @Autowired
    private IPLogService ipLogService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // IP Adresi, URL ve karakter bilgisi
        String ipAddress = httpRequest.getRemoteAddr();
        String url = httpRequest.getRequestURI();
        String characterId = httpRequest.getParameter("characterId");

        // Loglama
        ipLogService.logRequest(ipAddress, url, characterId);

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
