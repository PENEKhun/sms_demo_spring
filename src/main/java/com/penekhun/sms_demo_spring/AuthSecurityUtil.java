package com.penekhun.sms_demo_spring;

import com.penekhun.sms_demo_spring.Repository.PhoneAuthLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthSecurityUtil {
    private final PhoneAuthLogRepository phoneAuthLogRepository;

    public boolean checkAuthAttemptsExceededByIP(String ip) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today_begin = LocalDateTime.of(now.getYear(),
                now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime today_end = LocalDateTime.of(now.getYear(),
                now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        long attempts = phoneAuthLogRepository.countByIpAndCreateDateBetween(ip, today_begin, today_end);
        return attempts >= 3;
    }

    public String getClientIP(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");

        //proxy 환경일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        //웹로직 서버일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr() ;
        }

        return ip;
    }

    public String makeAuthCode() {
       return numberGen(6);
    }

    /**
     * 전달된 파라미터에 맞게 난수를 생성한다
     * @param len : 생성할 난수의 길이
     * @return 랜덤 난수
    **/
    private String numberGen(int len) {
        Random rand = null;
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            log.error("암호화 알고리즘 없음");
        }

        StringBuilder numStr = new StringBuilder(); //난수가 저장될 변수
        for(int i=0;i<len;i++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(Objects.requireNonNull(rand).nextInt(10));
            numStr.append(ran);
        }
        return numStr.toString();
    }

}
