package com.witeam.ai.function.call;

import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weTeam
 * @since 2024/11/5
 **/
@Configuration
public class SpringAIConfig {
    /**
     * 通过FunctionCallback bean 注册函数到AI model
     *
     * @return FunctionCallback
     */
    @Bean
    public FunctionCallback weatherFunctionInfo() {

        // By default, the response converter performs a JSON serialization of the Response object.
        return FunctionCallbackWrapper.builder(new MockWeatherService())
                .withName("CurrentWeather") // (1) function name
                .withDescription("Get the weather in location") // (2) function description
                .withSchemaType(FunctionCallbackContext.SchemaType.JSON_SCHEMA)
                .withInputType(Request.class) // (3) function input type
                .build();
    }

}
