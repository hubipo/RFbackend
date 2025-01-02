package org.links.usermanagementservice.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String phoneNumber;
    private String password;
    private String verificationCode;
}
