package org.links.usermanagementservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String phoneNumber;
    private String password;
    private String verificationCode;

    private String nickname;
    private String gender;
    private LocalDate birthDate;
    private String signature;
    private String avatarUrl;
}
