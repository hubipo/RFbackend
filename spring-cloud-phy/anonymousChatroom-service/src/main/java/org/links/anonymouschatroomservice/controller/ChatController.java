package org.links.anonymouschatroomservice.controller;


import org.links.anonymouschatroomservice.dto.MatchRequest;
import org.links.anonymouschatroomservice.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    public ChatController(ChatService chatService) {this.chatService = chatService;}

    @PostMapping("/startMatch")
    public ResponseEntity<?> Match(@RequestBody MatchRequest request ) {
        chatService.match(request);
        return ResponseEntity.ok().body("匹配开始");
    }

    @PostMapping("/endMatch")
    public ResponseEntity<?> EndMatch(@RequestParam String phoneNumber) {
        chatService.endMatch(phoneNumber);
        return ResponseEntity.ok().body("匹配已取消");
    }

}
