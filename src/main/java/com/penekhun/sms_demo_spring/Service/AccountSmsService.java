package com.penekhun.sms_demo_spring.Service;

import com.penekhun.sms_demo_spring.AuthSecurityUtil;
import com.penekhun.sms_demo_spring.Dto.PhoneAuthDto;
import com.penekhun.sms_demo_spring.Repository.PhoneAuthLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountSmsService {
    @Value("${CoolSMS.public_key}")
    private String COOLSMS_PUBLIC_KEY;
    @Value("${CoolSMS.secret_key}")
    private String COOLSMS_SECRET_KEY;
    @Value("${CoolSMS.sender}")
    private String COOLSMS_SENDER;
    private final AuthSecurityUtil authSecurityUtil;
    private final PhoneAuthLogRepository phoneAuthLogRepository;

    /**
     * 핸드폰 인증 요청 메서드
     * @param phoneNumber : 핸드폰 번호 (예 : "010-1234-1234")
     * @param request : http 요청 객체
     * @return 인증코드 문자열
     */
    @Transactional
    public String sendAuthCode(String phoneNumber, HttpServletRequest request) {
        // 일일 전송 횟수 제한
        String clientIP = authSecurityUtil.getClientIP(request);
        if (authSecurityUtil.checkAuthAttemptsExceededByIP(clientIP))
            throw new RuntimeException("인증 시도 횟수 초과");

        // 인증 코드 전송
        String authCode = authSecurityUtil.makeAuthCode();
        PhoneAuthDto.SMSRequest smsRequest = PhoneAuthDto.SMSRequest.builder()
                .to(phoneNumber)
                .from(COOLSMS_SENDER)
                .type("sms")
                .text("인증번호 [" + authCode + "]를 입력해주세요.")
                .build();
        try {
            Message coolsms = new Message(COOLSMS_PUBLIC_KEY, COOLSMS_SECRET_KEY);
            coolsms.send(smsRequest.of());
        } catch (CoolsmsException e) {
            throw new RuntimeException("인증 코드 전송 실패");
        }

        // 인증 코드 저장
        PhoneAuthDto.SaveAuthReq saveAuthReq = PhoneAuthDto.SaveAuthReq.builder()
                .phone(phoneNumber)
                .code(authCode)
                .expireAt(LocalDateTime.now().plusMinutes(5))
                .ip(clientIP)
                .build();
        phoneAuthLogRepository.save(saveAuthReq.toEntity());

        return authCode;
    }
}
