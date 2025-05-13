package org.links.consultationservice.service;

import org.links.consultationservice.dto.ConversationDto;
import org.links.consultationservice.dto.ConversationSearchResult;
import org.links.consultationservice.entity.Conversation;
import org.links.consultationservice.entity.MessageEntity;
import org.links.consultationservice.repository.ConversationRepository;
import org.links.consultationservice.repository.MessageRepository;
import org.links.consultationservice.repository.ConversationRepositoryCustom;
import org.links.consultationservice.util.QwenUtil;
import org.links.consultationservice.config.DashScopeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepositoryCustom conversationRepoCustom;

    @Autowired
    private DashScopeConfig dashScopeConfig;

    @Autowired
    private ConversationRepository conversationRepo;

    @Autowired
    private MessageRepository messageRepo;

    public Conversation startConversation(Long userId) {
        Conversation conversation = new Conversation();
        conversation.setUserId(userId);
        conversation.setTitle("新对话");
        conversation.setCreatedAt(LocalDateTime.now());
        return conversationRepo.save(conversation);
    }

    public ConversationDto startConversationDto(Long userId) {
        Conversation c = startConversation(userId);
        return new ConversationDto(c);
    }

    public List<MessageEntity> getMessages(Long conversationId) {
        Conversation conversation = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在"));
        return messageRepo.findByConversationOrderByTimestampAsc(conversation);
    }

    public void deleteConversation(Long id) {
        conversationRepo.deleteById(id); // 自动级联删除关联 messages
    }



    public String chat(Long conversationId, String userMessage) {
        // 获取会话对象
        Conversation conversation = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在"));

        // 获取消息历史（按时间排序）
        List<MessageEntity> history = messageRepo.findByConversationOrderByTimestampAsc(conversation);

        // 转换为千问模型格式
        List<com.alibaba.dashscope.common.Message> messages = history.stream()
                .map(m -> QwenUtil.toMessage(m.getRole(), m.getContent()))
                .collect(Collectors.toList());

        // 添加用户输入
        messages.add(QwenUtil.toMessage("user", userMessage));

        // 调用模型
        String reply;
        try {
            reply = QwenUtil.chatWithQwen(messages, dashScopeConfig.getApiKey());
        } catch (Exception e) {
            reply = "调用模型失败: " + e.getMessage();
        }

        // 保存对话记录
        messageRepo.save(new MessageEntity(conversation, "user", userMessage));
        messageRepo.save(new MessageEntity(conversation, "assistant", reply));

        return reply;
    }

    /// /////////
    public Conversation updateTitle(Long id, String newTitle) {
        Conversation conv = conversationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("会话不存在"));
        conv.setTitle(newTitle);
        return conversationRepo.save(conv);
    }

    public List<ConversationDto> getAllConversationsDto(Long userId) {
        return conversationRepo.findByUserId(userId)
                .stream()
                .map(ConversationDto::new)
                .toList();
    }

    public List<ConversationSearchResult> searchWithMultipleKeywords(Long userId, String rawKeyword) {
        List<String> keywords = Arrays.stream(rawKeyword.split("\\s+"))
                .filter(k -> !k.isBlank())
                .collect(Collectors.toList());
        return conversationRepoCustom.searchByKeywords(keywords, userId);
    }
}
