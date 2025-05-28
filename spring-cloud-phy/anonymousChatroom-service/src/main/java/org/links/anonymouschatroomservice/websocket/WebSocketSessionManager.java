package org.links.anonymouschatroomservice.websocket;

import jakarta.websocket.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessionManager {
    // 保存 userId -> WebSocket 对象的映射
    private static final Map<Long, Session> sessionMap = new ConcurrentHashMap<>();

    // 保存 userId -> toUserId 的绑定关系（聊天对象）
    private static final Map<Long, Long> toUserMap = new ConcurrentHashMap<>();

    public static void registerSession(Long userId, Session session) {
        sessionMap.put(userId, session);
    }

    public static void removeSession(Long userId) {
        sessionMap.remove(userId);
        toUserMap.remove(userId);
    }

    public static void bindUsersToChat(Long userA, Long userB) {
        toUserMap.put(userA, userB);
        toUserMap.put(userB, userA);
    }

    public static Long getToUserId(Long userId) {
        return toUserMap.get(userId);
    }

    public static void sendMatchSuccess(Long userId, Long chatRelationId) {
        Session session = sessionMap.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String message = "{\"type\":\"MATCH_SUCCESS\", \"chatRelationId\":" + chatRelationId + "}";
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessageToUser(Long userId, String message) {
        Session session = sessionMap.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isOnline(Long userId) {
        Session session = sessionMap.get(userId);
        return session != null && session.isOpen();
    }
}

