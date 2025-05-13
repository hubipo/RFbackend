package org.links.consultationservice.util;

import com.alibaba.dashscope.aigc.generation.*;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.exception.*;

import java.util.List;

public class QwenUtil {
    private static final String MODEL = "qwen-plus-latest";

    public static String chatWithQwen(List<Message> messages, String apiKey)
            throws ApiException, NoApiKeyException, InputRequiredException {

        Generation gen = new Generation();
        GenerationParam param = GenerationParam.builder()
                .apiKey(apiKey)
                .model(MODEL)
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();

        GenerationResult result = gen.call(param);
        return result.getOutput().getChoices().get(0).getMessage().getContent();
    }

    public static Message toMessage(String role, String content) {
        return Message.builder()
                .role(role)
                .content(content)
                .build();
    }
}
