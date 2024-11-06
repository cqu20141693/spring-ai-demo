package com.witeam.ai.client;

import com.witeam.ai.AiApp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.witeam.ai.ChatResponseUtils.print;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ChatClient测试类
 * 聊天大模型交互的统一接口，流式编程模式，提供了同步和响应式两种方式，
 * 内部维护一个ChatModel，在使用上更为方便，
 * 返回值获取、返回值格式化上都相当方便。
 *
 * @author weTeam
 * @since 2024/11/4
 **/
@SpringBootTest(classes = AiApp.class)
@Slf4j
class ClientTest {

    @Resource
    private ChatClient chatClient;


    @Resource
    private VectorStore vectorStore;

    @Test
    @DisplayName("testContentApi")
    void testContentApi() {
        String userText = "你好";
        String response = chatClient.prompt()
                .user(userText)
                .call()
                // content API
                .content();
        log.info("chat response: {}", response);
    }

    @Test
    @DisplayName("testChatResponseApi")
    void testChatResponseApi() {
        String userText = "你好";
        ChatResponse response = chatClient.prompt()
                .user(userText)
                .call()
                // content API
                .chatResponse();
        assertNotNull(response);
        print(response);
    }

    @Test
    @DisplayName("testResponseEntityApi")
    void testResponseEntityApi() {
        String userText = "你好";
        ResponseEntity<ChatResponse, String> entity = chatClient.prompt()
                .user(userText)
                .call()
                // content API
                .responseEntity(String.class);
        log.info("entity: {}", entity.getEntity());
        assertNotNull(entity.getResponse());
        print(entity.getResponse());
    }


    @Test
    void testGeneration() {
        ChatResponse chatResponse = chatClient.prompt().call().chatResponse();
        assertNotNull(chatResponse);

        Map<String, Generation> generation = Map.of("generation", chatResponse.getResult());
        log.info("generation: {}", generation);
    }

    @Test
    void testOutputParser() {


        String actor = "Jeff Bridges";

        var outputParser = new BeanOutputConverter<>(ActorsFilms.class);

        String format = outputParser.getFormat();
        log.info("format: " + format);
        String userMessage = """
                Generate the filmography for the actor {actor}.
                {format}
                """;
        PromptTemplate promptTemplate = new PromptTemplate(userMessage, Map.of("actor", actor, "format", format));
        Prompt prompt = promptTemplate.create();
        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        assertNotNull(chatResponse);
        List<Generation> generations = chatResponse.getResults();
        ArrayList<ActorsFilms> actorsFilms = new ArrayList<>();
        for (Generation generation : generations) {
            ActorsFilms e = outputParser.convert(generation.getOutput().getContent());
            actorsFilms.add(e);
        }

        log.info("list: {}", actorsFilms);
    }

    /**
     * 测试RAG
     */
    @Test
    void testAdvisor() {
        String userText = "你好";
        ChatResponse response = chatClient.prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
                .user(userText)
                .call()
                .chatResponse();
        log.info("chat response: {}", response);
    }


}
