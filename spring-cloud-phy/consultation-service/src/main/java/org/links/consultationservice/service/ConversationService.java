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

/**
 * 会话服务类。
 * <p>负责处理会话的创建、对话记录、AI应答、关键词搜索等核心逻辑。</p>
 */
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

    /**
     * 创建一个新的会话对象并保存。
     *
     * @param userId 用户 ID
     * @return 保存后的会话对象
     */
    public Conversation startConversation(Long userId) {
        Conversation conversation = new Conversation();
        conversation.setUserId(userId);
        conversation.setTitle("新对话");
        conversation.setCreatedAt(LocalDateTime.now());
        return conversationRepo.save(conversation);
    }

    /**
     * 创建新会话并返回 DTO 格式。
     *
     * @param userId 用户 ID
     * @return 新创建的会话 DTO
     */
    public ConversationDto startConversationDto(Long userId) {
        Conversation c = startConversation(userId);
        return new ConversationDto(c);
    }

    /**
     * 获取某会话的所有消息（按时间升序）。
     *
     * @param conversationId 会话 ID
     * @return 消息列表
     */
    public List<MessageEntity> getMessages(Long conversationId) {
        Conversation conversation = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在"));
        return messageRepo.findByConversationOrderByTimestampAsc(conversation);
    }

    /**
     * 删除指定会话（级联删除其消息）。
     *
     * @param id 会话 ID
     */
    public void deleteConversation(Long id) {
        conversationRepo.deleteById(id);
    }

    /**
     * 向千问模型发起对话请求并保存记录。
     *
     * @param conversationId 会话 ID
     * @param userMessage    用户输入
     * @return 模型的回复内容
     */
    public String chat(Long conversationId, String userMessage) {
        // 获取会话对象
        Conversation conversation = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("会话不存在"));

        // 获取历史记录并转为千问格式
        List<MessageEntity> history = messageRepo.findByConversationOrderByTimestampAsc(conversation);
        List<com.alibaba.dashscope.common.Message> messages = history.stream()
                .map(m -> QwenUtil.toMessage(m.getRole(), m.getContent()))
                .collect(Collectors.toList());
        messages.add(QwenUtil.toMessage("user", userMessage));

        // 生成 AI 回复
        String reply;
        try {
            reply = QwenUtil.chatWithQwen(messages, dashScopeConfig.getApiKey());
        } catch (Exception e) {
            reply = "调用模型失败: " + e.getMessage();
        }

        // 保存用户消息和 AI 回复
        messageRepo.save(new MessageEntity(conversation, "user", userMessage));
        messageRepo.save(new MessageEntity(conversation, "assistant", reply));

        return reply;
    }

    /**
     * 更新指定会话的标题。
     *
     * @param id       会话 ID
     * @param newTitle 新标题
     * @return 更新后的会话对象
     */
    public Conversation updateTitle(Long id, String newTitle) {
        Conversation conv = conversationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("会话不存在"));
        conv.setTitle(newTitle);
        return conversationRepo.save(conv);
    }

    /**
     * 获取某个用户的所有会话（DTO格式）。
     *
     * @param userId 用户 ID
     * @return 会话 DTO 列表
     */
    public List<ConversationDto> getAllConversationsDto(Long userId) {
        return conversationRepo.findByUserId(userId)
                .stream()
                .map(ConversationDto::new)
                .toList();
    }

    /**
     * 多关键词搜索某用户的所有会话标题或消息内容。
     *
     * @param userId     用户 ID
     * @param rawKeyword 多个关键词（以空格分隔）
     * @return 搜索结果摘要列表
     */
    public List<ConversationSearchResult> searchWithMultipleKeywords(Long userId, String rawKeyword) {
        List<String> keywords = Arrays.stream(rawKeyword.split("\\s+"))
                .filter(k -> !k.isBlank())
                .collect(Collectors.toList());
        return conversationRepoCustom.searchByKeywords(keywords, userId);
    }
}
