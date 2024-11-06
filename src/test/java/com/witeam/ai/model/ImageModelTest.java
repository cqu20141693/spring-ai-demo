package com.witeam.ai.model;

import com.witeam.ai.AiApp;
import com.witeam.common.test.BaseMockApi;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;

import static com.witeam.ai.ChatResponseUtils.print;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 图片模型测试类
 * 主要完成文生图功能
 *
 * @author weTeam
 * @since 2024/11/5
 **/
@SpringBootTest(classes = {AiApp.class})
class ImageModelTest implements BaseMockApi {


    @Resource
    private ChatClient chatClient;



    @Test
    @DisplayName("testImageModel")
    void testMutilModelImageAly() {
        String path = "/img.png";
        var imageResource = new ClassPathResource(path);

        var userMessage = new UserMessage(
                "Explain what do you see in this picture?", // content
                new Media(MimeTypeUtils.IMAGE_PNG, imageResource)); // media

        ChatResponse response = chatClient.prompt(new Prompt(userMessage)).call().chatResponse();
        assertNotNull(response);
        print(response);

        String respContent = chatClient.prompt()
                .user(u -> u.text("Explain what do you see on this picture?")
                        .media(MimeTypeUtils.IMAGE_PNG, new ClassPathResource("/multimodal.test.png")))
                .call()
                .content();

        log.info("respContent: {}", respContent);
    }
}
