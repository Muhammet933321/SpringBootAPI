package com.Blix.UnityAPI.repositories;

import com.Blix.UnityAPI.entities.IPLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPLogRepository extends JpaRepository<IPLog, Long> {
}
