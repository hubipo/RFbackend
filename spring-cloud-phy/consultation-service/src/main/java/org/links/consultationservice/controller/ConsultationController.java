package org.links.consultationservice.controller;

import org.links.consultationservice.dto.ConversationDto;
import org.links.consultationservice.dto.ConversationSearchResult;
import org.links.consultationservice.entity.Conversation;
import org.links.consultationservice.entity.MessageEntity;
import org.links.consultationservice.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {

    @Autowired
    private ConversationService conversationService;

    // 创建新会话（返回 DTO）
    @PostMapping("/conversations")
    public ResponseEntity<ConversationDto> startConversation(@RequestParam Long userId) {
        return ResponseEntity.ok(conversationService.startConversationDto(userId));
    }

    @DeleteMapping("/conversations/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Long id) {
        conversationService.deleteConversation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/conversations/{id}/messages")
    public ResponseEntity<String> chat(@PathVariable Long id, @RequestBody String userMessage) {
        return ResponseEntity.ok(conversationService.chat(id, userMessage));
    }

    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<List<MessageEntity>> getMessages(@PathVariable Long id) {
        return ResponseEntity.ok(conversationService.getMessages(id));
    }

    // 编辑聊天标题
    @PutMapping("/conversations/{id}")
    public ResponseEntity<Conversation> updateTitle(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(conversationService.updateTitle(id, body.get("title")));
    }

    // 获取某用户所有会话
    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getUserConversations(@RequestParam Long userId) {
        return ResponseEntity.ok(conversationService.getAllConversationsDto(userId));
    }

    // 多关键词搜索 + 匹配片段
    @GetMapping("/conversations/keyword-snippet-search")
    public ResponseEntity<List<ConversationSearchResult>> searchMultiKeyword(
            @RequestParam Long userId,
            @RequestParam String keyword) {
        return ResponseEntity.ok(conversationService.searchWithMultipleKeywords(userId, keyword));
    }
}
