package com.witeam.ai.model;

import java.util.List;

/**
 * 主题消息
 *
 * @author weTeam
 * @since 2024/11/4
 **/
public record TopicMessages(String topic, List<String> messages) {
}
