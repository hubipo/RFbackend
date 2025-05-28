package org.links.usermanagementservice.repository;

import org.links.usermanagementservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户实体的数据访问接口。
 * <p>继承自 {@link JpaRepository}，提供对 {@link User} 实体的基本增删改查操作。</p>
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据手机号查找用户。
     *
     * @param phoneNumber 用户手机号
     * @return 匹配的用户（若存在）
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
}
