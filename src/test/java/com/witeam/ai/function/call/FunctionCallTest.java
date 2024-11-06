package com.witeam.ai.function.call;

import com.witeam.ai.AiApp;
import com.witeam.common.test.BaseMockApi;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 函数调用测试类
 * 并不是所有模型都支持函数调用的。
 * why: 大模型会对内容解析，然后生成调用对象，调用本地方法。
 *
 * @author weTeam
 * @since 2024/11/5
 **/
@SpringBootTest(classes = AiApp.class)
class FunctionCallTest implements BaseMockApi {


    @Resource
    private ChatClient chatClient;

    /**
     * 测试函数调用，
     * 使用函数调用时，需要注册回调函数到AI Model中
     */
    @Test
    @DisplayName("testFunctionCall")
    void testFunctionCall() {

        //The above user question will trigger 3 calls to the CurrentWeather function
        String content = "What's the weather like in San Francisco, Tokyo, and Paris?";
        ChatResponse response = this.chatClient.prompt(content)
                .functions("CurrentWeather") // Enable the function
                .call()
                .chatResponse();
        assertNotNull(response);
        print(response);
    }

    private static void print(ChatResponse response) {
        response.getResults().forEach(ret -> {
            log.info("{}", ret.getOutput());
        });
    }

    @Test
    @DisplayName("testFuncCallWithToolContext")
    void testFuncCallWithToolContext() {
        BiFunction<Request, ToolContext, Response> weatherFunction =
                (request, toolContext) -> {
                    String sessionId = (String) toolContext.getContext().get("sessionId");
                    String userId = (String) toolContext.getContext().get("userId");
                    log.info("sessionId: {}, userId: {},request:{}", sessionId, userId, request);
                    // Use sessionId and userId in your function logic
                    double temperature = 0;
                    if (request.location().contains("Paris")) {
                        temperature = 15;
                    } else if (request.location().contains("Tokyo")) {
                        temperature = 10;
                    } else if (request.location().contains("San Francisco")) {
                        temperature = 30;
                    }
                    return new Response(temperature, Unit.C);
                };
        ChatResponse response = chatClient.prompt("What's the weather like in San Francisco, Tokyo, and Paris?")
                // 动态注入bean
                .functions(FunctionCallbackWrapper.builder(weatherFunction)
                        .withSchemaType(FunctionCallbackContext.SchemaType.JSON_SCHEMA)
                        .withInputType(Request.class)
                        .withName("getCurrentWeather")
                        .withDescription("Get the weather in location")
                        .build())
                // 注入工具上下文
                .toolContext(Map.of("sessionId", "1234", "userId", "5678"))
                .call()
                .chatResponse();
        assertNotNull(response);

        response.getResults().forEach(ret -> {
            log.info("{}", ret.getOutput());
        });
    }
}
