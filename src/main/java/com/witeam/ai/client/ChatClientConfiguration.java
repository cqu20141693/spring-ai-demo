package com.witeam.ai.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weTeam
 * @since 2024/11/4
 **/
@Configuration
public class ChatClientConfiguration {


    /**
     * Chat Client API:
     * building up the constituent parts of a Prompt
     * that is passed to the AI model as input.
     *
     * @param builder 构造器
     * @return client
     */
    @Bean
    @ConditionalOnMissingBean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}
