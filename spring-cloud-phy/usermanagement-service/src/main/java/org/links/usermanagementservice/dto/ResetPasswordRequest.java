package org.links.usermanagementservice.dto;

import lombok.Data;

/**
 * 重置密码请求数据传输对象。
 * <p>用于用户通过验证码验证后设置新密码。</p>
 */
@Data
public class ResetPasswordRequest {

    /**
     * 用户手机号。
     * <p>用于标识要重置密码的用户。</p>
     */
    private String phoneNumber;

    /**
     * 验证码。
     * <p>用于验证用户身份的短信验证码。</p>
     */
    private String verificationCode;

    /**
     * 新密码。
     * <p>用户设置的新登录密码。</p>
     */
    private String newPassword;
}
