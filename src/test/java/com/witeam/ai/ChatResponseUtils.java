package com.witeam.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;

/**
 * @author weTeam
 * @since 2024/11/5
 **/
@Slf4j
public class ChatResponseUtils {
    public static void print(ChatResponse response) {
        response.getResults().forEach(ret -> {
            log.info("{}", ret.getOutput());
        });
    }
}
