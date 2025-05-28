package org.links.usermanagementservice.dto;

import lombok.Data;

/**
 * 用户登录请求数据传输对象。
 * <p>用于接收前端提交的登录信息，支持手机号 + 密码 或 手机号 + 验证码两种方式。</p>
 */
@Data
public class LoginRequest {

    /**
     * 用户手机号。
     * <p>用于标识登录用户。</p>
     */
    private String phoneNumber;

    /**
     * 用户密码。
     * <p>可选字段，与验证码二选一，用于密码登录。</p>
     */
    private String password;

    /**
     * 验证码。
     * <p>可选字段，与密码二选一，用于验证码登录。</p>
     */
    private String verificationCode;
}
