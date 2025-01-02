package org.links.usermanagementservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String nickname;
    private String avatarUrl;
    private String gender;
    private int age;
    private String signature;
}
