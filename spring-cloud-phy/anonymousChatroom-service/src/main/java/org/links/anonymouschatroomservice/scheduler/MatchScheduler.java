package org.links.anonymouschatroomservice.scheduler;


import org.links.anonymouschatroomservice.service.ChatService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MatchScheduler {
    private final ChatService chatService;

    public MatchScheduler(ChatService chatService) {
        this.chatService = chatService;
    }

    // 每秒尝试匹配一次
    @Scheduled(fixedRate = 200)
    public void doMatch() {
        chatService.attemptMatch();
    }
}
