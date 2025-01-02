package org.links.usermanagementservice.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String phoneNumber;
    private String verificationCode;
    private String newPassword;
}
