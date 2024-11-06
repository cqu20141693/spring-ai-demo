package com.witeam.ai.vector;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.SearchRequest;

/**
 * @author weTeam
 * @since 2024/11/5
 **/
@Slf4j
class VectorUnitTest {

    @Test
    void testSearchRequest() {
        SearchRequest request = SearchRequest.query("Spring").withTopK(2);
        log.info("Similar documents: {}", request);
    }
}
