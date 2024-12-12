package com.Blix.UnityAPI.loggers;

import com.Blix.UnityAPI.entities.IpLog;
import com.Blix.UnityAPI.entities.User;
import com.Blix.UnityAPI.repositories.UserRepository;
import com.Blix.UnityAPI.services.IpLogService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@WebFilter("/*") // Tüm istekleri dinle
public class IpLoggingFilter implements Filter {

    @Autowired
    private IpLogService ipLogService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // IP ve URL bilgilerini al
        String ipAddress = httpRequest.getRemoteAddr();
        String url = httpRequest.getRequestURI();

        // İstekten karakter ID'sini al
        String characterIdParam = httpRequest.getParameter("characterId");

        // Karakter ID'yi kontrol et
        if (characterIdParam != null) {
            try {
                Long characterId = Long.parseLong(characterIdParam);

                // Karakter ID'ye sahip kullanıcıyı bul
                Optional<User> user = userRepository.findById(characterId);
                if (user.isPresent()) {
                    ipLogService.saveIpLog(ipAddress, url, user.get());
                } else {
                    ipLogService.saveIpLog(ipAddress, url, null); // Kullanıcı bulunamadıysa logla
                }
            } catch (NumberFormatException e) {
                ipLogService.saveIpLog(ipAddress, url, null); // Geçersiz karakter ID'si
            }
        } else {
            // Karakter ID yoksa "NotAuth" olarak logla
            ipLogService.saveIpLog(ipAddress, url, null);
        }

        // İsteği zincire gönder
        chain.doFilter(request, response);
    }
}
