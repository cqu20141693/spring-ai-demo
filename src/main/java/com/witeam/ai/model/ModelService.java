package com.witeam.ai.model;

import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * 模型服务
 *
 * @author weTeam
 * @since 2024/11/4
 **/
public interface ModelService<T> {
    /**
     * 生成输出
     *
     * @param input 用户输入
     * @return 生成输出
     */
    String generation(String input);
    ChatResponse generationChatResponse(String input);
    T generationEntity(String input);

    Flux<String> generationFlux(String input);
}
