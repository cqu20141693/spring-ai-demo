package com.witeam.ai.function.call;

import com.fasterxml.jackson.annotation.JsonClassDescription;

/**
 * @author weTeam
 * @since 2024/11/5
 **/

@JsonClassDescription("response the weather info") // // function description
public record Response(double temp, Unit unit) {
}
