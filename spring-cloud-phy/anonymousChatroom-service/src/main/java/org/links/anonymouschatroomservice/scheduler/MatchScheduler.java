package org.links.anonymouschatroomservice.scheduler;

import org.links.anonymouschatroomservice.service.MatchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MatchScheduler {
    private final MatchService matchService;

    public MatchScheduler(MatchService matchService) {
        this.matchService = matchService;
    }

    // 每秒尝试匹配一次
    @Scheduled(fixedRate = 1000)
    public void doMatch() {
        matchService.attemptMatch();
    }
}
