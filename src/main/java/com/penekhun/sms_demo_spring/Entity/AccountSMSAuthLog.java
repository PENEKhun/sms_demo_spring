package com.penekhun.sms_demo_spring.Entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "account_sms_auth_log")
@EntityListeners(AuditingEntityListener.class)
public class AccountSMSAuthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "phone", nullable = false, updatable = false)
    private String phone;

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "expire_at", updatable = false)
    private LocalDateTime expireAt;

    @Column(name = "ip", nullable = true, updatable = false)
    private String ip;

    @Builder
    public AccountSMSAuthLog(String phone, String code, LocalDateTime createDate, LocalDateTime expireAt, String ip) {
        this.phone = phone;
        this.code = code;
        this.createDate = createDate;
        this.expireAt = expireAt;
        this.ip = ip;
    }
}
