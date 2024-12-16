package com.Blix.UnityAPI.services;

import com.Blix.UnityAPI.entities.IPLog;
import com.Blix.UnityAPI.repositories.IPLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IPLogService {

    @Autowired
    private IPLogRepository ipLogRepository;

    public void logRequest(String ipAddress, String url, String characterId) {
        IPLog log = new IPLog();
        log.setIpAddress(ipAddress);
        log.setUrl(url);
        log.setTimestamp(LocalDateTime.now());
        log.setCharacterId(characterId == null ? "NotAuth" : characterId);

        ipLogRepository.save(log);
    }
}
