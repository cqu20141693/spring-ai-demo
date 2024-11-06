package com.witeam.ai.rag;

import com.witeam.ai.AiApp;
import com.witeam.common.test.BaseMockApi;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author weTeam
 * @since 2024/11/5
 **/
@SpringBootTest(classes = AiApp.class)
public class RagTest implements BaseMockApi {

    @Resource
    private ChatClient chatClient;

    @Resource
    private EmbeddingModel embeddingModel;

    public void test() {

        String message = "What bike is good for city commuting?";
        RagService ragService = new RagService(chatClient, embeddingModel);
        AssistantMessage retrieve = ragService.retrieve(message);
        assertNotNull(retrieve);
        log.info("retrieve:{}", retrieve);
    }
}
