package org.links.anonymouschatroomservice.service;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.links.anonymouschatroomservice.dto.MatchRequest;
import org.links.anonymouschatroomservice.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ChatService {

    private final UserClient userClient;
    private final MqttService mqttService;

    public ChatService(UserClient userClient, MqttService mqttService) {
        this.userClient = userClient;
        this.mqttService = mqttService;
    }

    //tag queue for matching
    private final Map<String, Queue<Long>> tagMatchQueue = new ConcurrentHashMap<>();

    /**
     * Helper to get the user ID by phone number.
     */
    private Long getUserIdByPhoneNumber(String phoneNumber) {
        try {
            return userClient.getUserIdByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            throw new CustomException("无法通过手机号获取用户 ID，请检查用户服务是否可用");
        }
    }

    public void match(MatchRequest request){
        Long userId = getUserIdByPhoneNumber(request.getPhoneNumber());
        String tag = request.getTag();

        tagMatchQueue.putIfAbsent(tag, new ConcurrentLinkedQueue<>());
        Queue<Long> queue = tagMatchQueue.get(tag);
        if(!queue.contains(userId)){
            queue.offer(userId);
        }
    }

    public void endMatch(String phoneNumber){
        Long userId = getUserIdByPhoneNumber(phoneNumber);
        for(Queue<Long> queue: tagMatchQueue.values()){
            queue.remove(userId);
        }
    }

    // 应由定时任务调用
    // 可用于记录已匹配的用户对（例如持久化、后续聊天使用）

    public void attemptMatch() {
        for (Map.Entry<String, Queue<Long>> entry : tagMatchQueue.entrySet()) {
            Queue<Long> queue = entry.getValue();
            while (queue.size() >= 2) {
                Long user1 = queue.poll();
                Long user2 = queue.poll();

                String roomId = "room-" + UUID.randomUUID(); //生成随机房间号(topic)

                try {
                    // 向用户推送匹配结果(topic)
                    mqttService.sendMessage("/chat/match/" + user1, roomId);
                    mqttService.sendMessage("/chat/match/" + user2, roomId);
                    System.out.println("配对成功：" + user1 + " <==> " + user2 + " in room " + roomId);
                }
                catch (MqttException e) {
                    System.out.println("发生异常：" + e.getMessage());
                }
            }
        }
    }
}
