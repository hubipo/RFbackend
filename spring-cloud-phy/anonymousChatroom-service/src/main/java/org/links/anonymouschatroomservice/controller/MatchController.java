package org.links.anonymouschatroomservice.controller;

import org.links.anonymouschatroomservice.dto.UserInfo;
import org.links.anonymouschatroomservice.service.MatchService;
import org.links.anonymouschatroomservice.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anonymouschatroom/match")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }
    @PostMapping("/start")
    public ResponseEntity<?> startMatch(@RequestHeader("Authorization") String jwt) {
        Long userId = JwtUtil.parse(jwt).get("userId", Long.class);
        String tag = JwtUtil.parse(jwt).get("tag", String.class);
        return matchService.enqueue(userId, tag);

//        Optional<UserInfo> partner = matchService.match(userId, tag);
//        if (partner.isPresent()) {
//            String chatId = matchService.createRelation(userId, partner.get().getUserId());
//            return ResponseEntity.ok(Map.of("chatId", chatId, "partner", partner.get()));
//        }
//        return ResponseEntity.status(204).build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelMatch(@RequestHeader("Authorization") String jwt) {
        Long userId = JwtUtil.parse(jwt).get("userId", Long.class);
        return matchService.dequeue(userId);
    }
}