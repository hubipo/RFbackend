package org.links.usermanagementservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置类。
 * <p>用于配置密码加密策略，当前使用 {@link BCryptPasswordEncoder} 进行加密。</p>
 */
@Configuration
public class SecurityConfig {

    /**
     * 密码加密器 Bean。
     * <p>用于用户密码的加密与验证。</p>
     *
     * @return BCrypt 加密器实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 用于密码加密
    }
}
