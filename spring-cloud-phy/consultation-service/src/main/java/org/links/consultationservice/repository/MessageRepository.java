package org.links.consultationservice.repository;

import org.links.consultationservice.entity.Conversation;
import org.links.consultationservice.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 消息实体（MessageEntity）的数据访问接口。
 * <p>提供对会话消息的持久化操作，支持按会话查找、时间排序及批量删除。</p>
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    /**
     * 查询指定会话下的所有消息，按时间升序排序（从最早到最晚）。
     *
     * @param conversation 会话实体
     * @return 按时间排序的消息列表
     */
    List<MessageEntity> findByConversationOrderByTimestampAsc(Conversation conversation);

    /**
     * 删除指定会话下的所有消息。
     *
     * @param conversation 会话实体
     */
    void deleteByConversation(Conversation conversation);
}
