package org.links.usermanagementservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户实体类。
 * <p>映射到数据库表 {@code users}，用于存储用户基本信息、权限状态和账号状态。</p>
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * 用户主键 ID，自增生成。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户手机号，唯一且不能为空。
     */
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    /**
     * 用户加密后的密码。
     */
    @Column(nullable = false)
    private String password;

    /**
     * 用户昵称，最长 50 个字符。
     */
    @Column(length = 50)
    private String nickname;

    /**
     * 用户性别（枚举）。
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * 用户出生日期。
     */
    private LocalDate birthDate;

    /**
     * 个性签名。
     */
    @Column(length = 255)
    private String signature;

    /**
     * 用户头像 URL。
     */
    @Column(length = 255)
    private String avatarUrl;

    /**
     * 用户角色（默认 "USER"）。
     */
    @Column(length = 20)
    private String role = "USER";

    /**
     * 用户当前状态（ACTIVE：正常，BANNED：封禁）。
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    /**
     * 封禁原因，仅当 status 为 BANNED 时填写。
     */
    @Column(length = 255)
    private String banReason;

    /**
     * 用户性别枚举。
     */
    public enum Gender {
        /**
         * 男性。
         */
        MALE,
        /**
         * 女性。
         */
        FEMALE,
        /**
         * 其他或未指定。
         */
        OTHER
    }

    /**
     * 用户状态枚举。
     */
    public enum Status {
        /**
         * 账号正常启用。
         */
        ACTIVE,
        /**
         * 账号已被封禁。
         */
        BANNED
    }
}
