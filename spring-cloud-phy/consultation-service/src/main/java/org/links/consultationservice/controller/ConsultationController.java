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

/**
 * 咨询服务接口
 */
@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {

    @Autowired
    private ConversationService conversationService;

    /**
     * C01 - 创建新会话
     *
     * @param userId 用户ID
     * @return 新建的会话信息（ConversationDto）
     */
    @PostMapping("/conversations")
    public ResponseEntity<ConversationDto> startConversation(@RequestParam Long userId) {
        return ResponseEntity.ok(conversationService.startConversationDto(userId));
    }

    /**
     * C02 - 删除会话
     *
     * @param id 会话ID
     * @return 204 No Content
     */
    @DeleteMapping("/conversations/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Long id) {
        conversationService.deleteConversation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * C03 - 向指定会话发送用户消息并获取模型回复
     *
     * @param id          会话ID
     * @param userMessage 用户输入内容（纯文本）
     * @return 模型返回的回答内容（纯文本）
     */
    @PostMapping("/conversations/{id}/messages")
    public ResponseEntity<String> chat(@PathVariable Long id, @RequestBody String userMessage) {
        return ResponseEntity.ok(conversationService.chat(id, userMessage));
    }

    /**
     * C04 - 获取指定会话的所有消息记录
     *
     * @param id 会话ID
     * @return 消息记录列表（MessageEntity）
     */
    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<List<MessageEntity>> getMessages(@PathVariable Long id) {
        return ResponseEntity.ok(conversationService.getMessages(id));
    }

    /**
     * C05 - 编辑会话标题
     *
     * @param id   会话ID
     * @param body 包含新的 title 字段的 JSON 对象
     * @return 更新后的会话实体（Conversation）
     */
    @PutMapping("/conversations/{id}")
    public ResponseEntity<Conversation> updateTitle(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(conversationService.updateTitle(id, body.get("title")));
    }

    /**
     * C06 - 获取用户所有会话（简略信息）
     *
     * @param userId 用户ID
     * @return 用户的所有会话列表（ConversationDto）
     */
    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getUserConversations(@RequestParam Long userId) {
        return ResponseEntity.ok(conversationService.getAllConversationsDto(userId));
    }

    /**
     * C07 - 多关键词搜索并返回匹配片段
     *
     * @param userId  用户ID
     * @param keyword 多关键词字符串，英文逗号分隔（如 "睡眠,焦虑"）
     * @return 匹配片段列表（ConversationSearchResult）
     */
    @GetMapping("/conversations/keyword-snippet-search")
    public ResponseEntity<List<ConversationSearchResult>> searchMultiKeyword(
            @RequestParam Long userId,
            @RequestParam String keyword) {
        return ResponseEntity.ok(conversationService.searchWithMultipleKeywords(userId, keyword));
    }
}
