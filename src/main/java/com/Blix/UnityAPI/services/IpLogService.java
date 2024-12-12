package com.Blix.UnityAPI.services;

import com.Blix.UnityAPI.entities.IpLog;
import com.Blix.UnityAPI.entities.User;
import com.Blix.UnityAPI.repositories.IpLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IpLogService {

    @Autowired
    private IpLogRepository ipLogRepository;

    public void saveIpLog(String ipAddress, String url, User user) {
        IpLog ipLog = new IpLog();
        ipLog.setIpAddress(ipAddress);
        ipLog.setUrl(url);
        ipLog.setTimestamp(LocalDateTime.now());

        if (user != null) {
            ipLog.setUser(user); // Kullanıcıyı ilişkilendir
        } else {
            ipLog.setIpAddress("NotAuth"); // Kullanıcı yoksa "NotAuth" kaydı
        }

        ipLogRepository.save(ipLog);
    }
}
