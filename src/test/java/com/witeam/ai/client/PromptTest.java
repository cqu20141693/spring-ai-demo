package com.witeam.ai.client;

import com.witeam.ai.AiApp;
import com.witeam.common.test.BaseMockApi;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author weTeam
 * @since 2024/11/5
 **/
@SpringBootTest(classes = AiApp.class)
class PromptTest implements BaseMockApi {

    @Value("classpath:/prompts/joke-prompt.st")
    private org.springframework.core.io.Resource jokeResource;
    @Value("classpath:/prompts/system-role-prompt.st")
    private org.springframework.core.io.Resource systemPrompt;
    @Value("classpath:/docs/wikipedia-curling.md")
    private org.springframework.core.io.Resource docsToStuffResource;

    @Value("classpath:/prompts/qa-prompt.st")
    private org.springframework.core.io.Resource qaPromptResource;

    @Resource
    private ChatClient chatClient;

    @Test
    @SneakyThrows
    @DisplayName("testPromptTemplate")
    void testPrompt() {

        String template = jokeResource.getContentAsString(StandardCharsets.UTF_8);
        PromptTemplate promptTemplate = new PromptTemplate(template);

        String adjective = "funny";
        String topic = "cow";
        Prompt prompt = promptTemplate.create(Map.of("adjective", adjective, "topic", topic));

        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        assertNotNull(chatResponse);
        List<Generation> result = chatResponse.getResults();
        for (Generation generation : result) {
            log.info("Prompt result: {}", generation.getOutput().getContent());
        }

    }

    @SneakyThrows
    @Test
    @DisplayName("testSystemRoleTemplate")
    void testSystemTemplate() {
        String userText = "Tell me about 3 famous physicists";
        Message userMessage = new UserMessage(userText);
        String systemText = systemPrompt.getContentAsString(StandardCharsets.UTF_8);

        String name = "Rick";
        String voice = "Rick Sanchez";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        assertNotNull(chatResponse);

        List<Generation> response = chatResponse.getResults();

        for (Generation generation : response) {
            log.info("System response: {}", generation.getOutput().getContent());
        }

    }

    @SneakyThrows
    @Test
    @DisplayName("testPromptQAWithContext")
    void testPromptQAWithContext() {
        String message = "Which athletes won the mixed doubles gold medal in curling at the 2022 Winter Olympics?";
        String context = docsToStuffResource.getContentAsString(StandardCharsets.UTF_8);

        PromptTemplate promptTemplate = new PromptTemplate(qaPromptResource);
        Map<String, Object> map = new HashMap<>();
        map.put("question", message);
        map.put("context", context);

        Prompt prompt = promptTemplate.create(map);
        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        assertNotNull(chatResponse);
        List<Generation> generations = chatResponse.getResults();
        for (Generation generation : generations) {
            log.info("Prompt QA result: {}", generation.getOutput().getContent());
        }
    }


}
