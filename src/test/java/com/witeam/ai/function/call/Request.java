package com.witeam.ai.function.call;

import com.fasterxml.jackson.annotation.JsonClassDescription;

/**
 * @author weTeam
 * @since 2024/11/5
 **/

@JsonClassDescription("Get the weather in location") // // function description
public record Request(String location, Unit unit) {
}
