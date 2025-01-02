package org.links.usermanagementservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String nickname;
    private String gender;
    private LocalDate birthDate;
    private String signature;
    private String avatarUrl;
}
