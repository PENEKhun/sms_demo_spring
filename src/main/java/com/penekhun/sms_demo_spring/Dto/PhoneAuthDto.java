package com.penekhun.sms_demo_spring.Dto;


import com.penekhun.sms_demo_spring.Entity.AccountSMSAuthLog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;

public class PhoneAuthDto {

    @Getter
    public static class SMSRequest {
        private final String to;
        private final String from;
        private final String type;
        private final String text;

        @Builder
        public SMSRequest(String to, String from, String type, String text) {
            this.to = to;
            this.from = from;
            this.type = type;
            this.text = text;
        }

        public HashMap<String, String> of(){
            HashMap<String, String> map = new HashMap<>();
            map.put("to", to);
            map.put("from", from);
            map.put("type", type);
            map.put("text", text);
            return map;
        }
    }

    public static class SaveAuthReq {
        private String phone;
        private String code;
        private LocalDateTime expireAt;
        private String ip;

        @Builder
        public SaveAuthReq(String phone, String code, LocalDateTime expireAt, String ip) {
            this.phone = phone;
            this.code = code;
            this.expireAt = expireAt;
            this.ip = ip;
        }

        public AccountSMSAuthLog toEntity(){
            return AccountSMSAuthLog.builder()
                    .phone(phone)
                    .code(code)
                    .expireAt(expireAt)
                    .ip(ip)
                    .build();
        }
    }
}
