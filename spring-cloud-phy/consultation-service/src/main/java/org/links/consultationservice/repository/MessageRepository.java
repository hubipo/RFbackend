package org.links.consultationservice.repository;

import org.links.consultationservice.entity.Conversation;
import org.links.consultationservice.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findByConversationOrderByTimestampAsc(Conversation conversation);

    void deleteByConversation(Conversation conversation);
}

