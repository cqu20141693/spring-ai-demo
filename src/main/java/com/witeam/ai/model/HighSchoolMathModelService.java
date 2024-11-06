package com.witeam.ai.model;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * 高中数学模型
 *
 * @author weTeam
 * @since 2024/11/4
 **/
@Component
public class HighSchoolMathModelService implements ModelService<TopicMessages> {

    private static final String HIGH_SCHOOL_MATH_PROMPT = "作为一个高中数学老师，回答学生提问";
    private final ChatClient client;

    public HighSchoolMathModelService(ChatClient client) {
        this.client = client;
    }

    @Override
    public String generation(String input) {
        return client.prompt(HIGH_SCHOOL_MATH_PROMPT)
                // 用户输入
                .user(input)
                // AI模型调用
                .call()
                // 模型回复解析为String
                .content()
                ;
    }

    @Override
    public ChatResponse generationChatResponse(String input) {
        return client.prompt(HIGH_SCHOOL_MATH_PROMPT)
                // 用户输入
                .user(input)
                // AI模型调用
                .call()
                // 模型回复解析为ChatResponse
                .chatResponse()
                ;
    }

    @Override
    public TopicMessages generationEntity(String input) {
        return client.prompt(HIGH_SCHOOL_MATH_PROMPT)
                // 用户输入
                .user(input)
                // AI模型调用
                .call()
                // 模型回复解析为entity
                .responseEntity(TopicMessages.class).entity()
                ;
    }

    @Override
    public Flux<String> generationFlux(String input) {
        return client.prompt(HIGH_SCHOOL_MATH_PROMPT)
                // 用户输入
                .user(input)
                // AI模型调用
                .stream()
                // 模型回复解析为String
                .content()
                ;
    }


}
