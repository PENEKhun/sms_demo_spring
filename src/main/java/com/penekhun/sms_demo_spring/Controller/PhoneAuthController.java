package com.penekhun.sms_demo_spring.Controller;

import com.penekhun.sms_demo_spring.Service.AccountSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PhoneAuthController {
    private final AccountSmsService phoneAuthService;

    @PostMapping("/auth/phone")
    public ResponseEntity<String> phoneAuth(String phoneNumber, HttpServletRequest request) {
        log.info("인증 시도, phone Number : {}", phoneNumber);
        String authCode = phoneAuthService.sendAuthCode(phoneNumber, request);
        return ResponseEntity.ok(authCode);
    }

}
