package org.links.anonymouschatroomservice.repository;

import org.links.anonymouschatroomservice.entity.ChatRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRelationRepository extends JpaRepository<ChatRelation,Long>{
    Optional<ChatRelation> findById(Long id);

    Optional<ChatRelation> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    Optional<ChatRelation> findByUser2IdAndUser1Id(Long user2Id, Long user1Id);

    // 用于双向查找
    default Optional<ChatRelation> findByUsers(Long userA, Long userB) {
        return findByUser1IdAndUser2Id(userA, userB)
                .or(() -> findByUser2IdAndUser1Id(userA, userB));
    }
}
