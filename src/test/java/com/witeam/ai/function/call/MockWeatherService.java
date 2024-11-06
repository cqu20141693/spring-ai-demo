package com.witeam.ai.function.call;

import java.util.function.Function;

/**
 * @author weTeam
 * @since 2024/11/5
 **/
public class MockWeatherService implements Function<Request, Response> {

    @Override
    public Response apply(Request response) {
        return new Response(30.0, Unit.C);
    }
}
