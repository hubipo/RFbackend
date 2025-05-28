package org.links.usermanagementservice.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 用户信息更新请求数据传输对象。
 * <p>用于用户修改个人资料时提交的信息，所有字段均为可选。</p>
 */
@Data
public class UpdateUserRequest {

    /**
     * 用户昵称。
     * <p>新的展示名称，用于替换原昵称。</p>
     */
    private String nickname;

    /**
     * 用户性别。
     * <p>合法值为 "MALE" 或 "FEMALE"。</p>
     */
    private String gender;

    /**
     * 出生日期。
     * <p>格式为 YYYY-MM-DD。</p>
     */
    private LocalDate birthDate;

    /**
     * 个性签名。
     * <p>用户的个性化介绍文本。</p>
     */
    private String signature;

    /**
     * 头像 URL。
     * <p>新的用户头像图片地址。</p>
     */
    private String avatarUrl;
}
