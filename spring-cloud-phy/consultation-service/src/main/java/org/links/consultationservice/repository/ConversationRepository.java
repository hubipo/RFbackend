package org.links.consultationservice.repository;

import feign.Param;
import org.links.consultationservice.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 会话数据访问接口，继承自 {@link JpaRepository}。
 * <p>用于操作 Conversation（对话会话）实体，支持基本的 CRUD 以及按用户查询和关键词搜索。</p>
 */
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    /**
     * 查询指定用户的所有会话列表。
     *
     * @param userId 用户 ID
     * @return 会话列表
     */
    List<Conversation> findByUserId(Long userId);

    /**
     * 分页查询指定用户的会话。
     *
     * @param userId   用户 ID
     * @param pageable 分页参数
     * @return 会话分页对象
     */
    Page<Conversation> findByUserId(Long userId, Pageable pageable);

    /**
     * 搜索指定用户的会话标题或消息内容中包含关键词的会话记录（不区分大小写）。
     *
     * <p>通过 JPQL 联合会话表和消息表进行模糊匹配查询，适用于关键词智能搜索。</p>
     *
     * @param userId  用户 ID
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 匹配结果的分页列表
     */
    @Query("""
        SELECT DISTINCT c FROM Conversation c
        LEFT JOIN c.messages m
        WHERE c.userId = :userId AND (
            LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(m.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<Conversation> searchByTitleOrMessage(@Param("userId") Long userId,
                                              @Param("keyword") String keyword,
                                              Pageable pageable);
}
