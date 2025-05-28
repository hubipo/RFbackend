package org.links.anonymouschatroomservice.controller;

import org.links.anonymouschatroomservice.dto.ChatMessageDto;
import org.links.anonymouschatroomservice.entity.ChatMessage;
import org.links.anonymouschatroomservice.service.MessageService;
import org.links.anonymouschatroomservice.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/anonymouschatroom/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService=messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody ChatMessageDto messageDto, @RequestHeader("Authorization") String jwt) {
        String fromUser = JwtUtil.parse(jwt).get("userId", String.class);
        messageService.saveMessage(fromUser, messageDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread")
    public List<ChatMessage> getUnread(@RequestHeader("Authorization") String jwt) {
        String userId = JwtUtil.parse(jwt).get("userId", String.class);
        return messageService.getUnreadMessages(userId);
    }

    @PostMapping("/mark-read")
    public ResponseEntity<?> markRead(@RequestBody List<Long> messageIds) {
        messageService.markAsRead(messageIds);
        return ResponseEntity.ok().build();
    }
}
