package com.penekhun.sms_demo_spring.Repository;

import com.penekhun.sms_demo_spring.Entity.AccountSMSAuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PhoneAuthLogRepository extends JpaRepository<AccountSMSAuthLog, Long> {
    long countByIpAndCreateDateBetween(String ip, LocalDateTime start, LocalDateTime end);
}
