package org.links.usermanagementservice.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 用户注册请求数据传输对象。
 * <p>用于接收用户注册时提交的信息，包括基本身份凭据和可选的个人资料。</p>
 */
@Data
public class RegisterRequest {

    /**
     * 用户手机号。
     * <p>注册账户所使用的手机号码。</p>
     */
    private String phoneNumber;

    /**
     * 用户设置的密码。
     * <p>用于后续密码登录验证。</p>
     */
    private String password;

    /**
     * 手机验证码。
     * <p>用于校验手机号的合法性和所有权。</p>
     */
    private String verificationCode;

    /**
     * 用户昵称（可选）。
     * <p>用于展示的用户名称。</p>
     */
    private String nickname;

    /**
     * 用户性别（可选）。
     * <p>合法值为 "MALE" 或 "FEMALE"。</p>
     */
    private String gender;

    /**
     * 用户出生日期（可选）。
     * <p>格式为 YYYY-MM-DD。</p>
     */
    private LocalDate birthDate;

    /**
     * 用户签名（可选）。
     * <p>用于个性化展示的文本介绍。</p>
     */
    private String signature;

    /**
     * 用户头像 URL（可选）。
     * <p>指向用户头像图片的网络地址。</p>
     */
    private String avatarUrl;
}
