package org.links.consultationservice.util;

import com.alibaba.dashscope.aigc.generation.*;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.exception.*;

import java.util.List;

/**
 * 千问模型工具类，封装模型调用逻辑和消息格式转换。
 */
public class QwenUtil {

    // 模型名称，可按需替换为 qwen-turbo 或其他
    private static final String MODEL = "qwen-plus-latest";

    /**
     * 向千问模型发起对话请求，获取回复内容。
     *
     * @param messages 消息历史（包括用户和 AI 的角色与内容）
     * @param apiKey   DashScope 提供的 API Key
     * @return 模型回复的文本内容
     * @throws ApiException           调用失败（如请求超时、配额不足）
     * @throws NoApiKeyException      未提供 API Key
     * @throws InputRequiredException 请求缺少必要参数
     */
    public static String chatWithQwen(List<Message> messages, String apiKey)
            throws ApiException, NoApiKeyException, InputRequiredException {

        Generation gen = new Generation();

        // 构造参数：指定模型、消息历史、结果格式
        GenerationParam param = GenerationParam.builder()
                .apiKey(apiKey)
                .model(MODEL)
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE) // 返回完整消息对象
                .build();

        // 发起请求并获取结果
        GenerationResult result = gen.call(param);

        // 提取第一个回复的内容文本
        return result.getOutput().getChoices().get(0).getMessage().getContent();
    }

    /**
     * 构造千问格式的消息对象。
     *
     * @param role    角色（如 "user" 或 "assistant"）
     * @param content 消息文本内容
     * @return 格式化后的 Message 对象
     */
    public static Message toMessage(String role, String content) {
        return Message.builder()
                .role(role)
                .content(content)
                .build();
    }
}
