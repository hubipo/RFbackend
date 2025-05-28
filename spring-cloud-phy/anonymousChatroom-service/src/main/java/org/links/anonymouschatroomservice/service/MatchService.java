package org.links.anonymouschatroomservice.service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.links.anonymouschatroomservice.entity.ChatRelation;
import org.links.anonymouschatroomservice.repository.ChatRelationRepository;
import org.links.anonymouschatroomservice.websocket.WebSocketSessionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
    private final Queue<Long> matchingQueue = new ConcurrentLinkedQueue<>();
    private final Map<Long, String> tagMap = new ConcurrentHashMap<>();
    private final ChatRelationRepository chatRelationRepository;

    public MatchService(ChatRelationRepository chatRelationRepository) {
        this.chatRelationRepository = chatRelationRepository;
    }

    public synchronized ResponseEntity<?> enqueue(Long userId, String tag) {
        tagMap.put(userId, tag);
        matchingQueue.offer(userId);
        return ResponseEntity.ok("加入匹配队列");
    }

    public synchronized ResponseEntity<?> dequeue(Long userId) {
        boolean removedFromQueue = matchingQueue.remove(userId);
        boolean removedFromTagMap = tagMap.remove(userId) != null;
        if (removedFromQueue || removedFromTagMap) {
            return ResponseEntity.ok("已取消匹配");
        } else {
            return ResponseEntity.badRequest().body("用户不在匹配队列中");
        }
    }

    // 应由定时任务调用
    public synchronized void attemptMatch() {
        for (Long user1 : matchingQueue) {
            for (Long user2 : matchingQueue) {
                if (!user1.equals(user2) && tagMap.containsKey(user1) && tagMap.containsKey(user2)
                        && tagMap.get(user1).equals(tagMap.get(user2))) {
                    matchingQueue.remove(user1);
                    matchingQueue.remove(user2);
                    tagMap.remove(user1);
                    tagMap.remove(user2);

                    ChatRelation relation = new ChatRelation();
                    relation.setUser1Id(user1);
                    relation.setUser2Id(user2);
                    relation.setActive(true);
                    chatRelationRepository.save(relation);

                    WebSocketSessionManager.bindUsersToChat(user1, user2);
                    WebSocketSessionManager.sendMatchSuccess(user1, relation.getId());
                    WebSocketSessionManager.sendMatchSuccess(user2, relation.getId());

                    return; // 一次匹配一个对
                }
            }
        }
    }
}
