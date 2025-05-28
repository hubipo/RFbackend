package org.links.usermanagementservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 用户信息响应数据传输对象。
 * <p>用于向前端返回用户的基本资料信息。</p>
 */
@Data
@Builder
public class UserResponse {

    /**
     * 用户唯一标识。
     */
    private Long id;

    /**
     * 用户昵称。
     */
    private String nickname;

    /**
     * 用户头像的 URL 地址。
     */
    private String avatarUrl;

    /**
     * 用户性别。
     * <p>可能值包括 "MALE" 或 "FEMALE"。</p>
     */
    private String gender;

    /**
     * 用户年龄。
     */
    private int age;

    /**
     * 用户个性签名。
     */
    private String signature;
}
