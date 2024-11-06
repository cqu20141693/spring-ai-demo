package com.witeam.ai.vector;

import com.witeam.ai.AiApp;
import com.witeam.common.test.BaseMockApi;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * 向量检索测试类
 * 向量数据库集成，比如像我们比较熟悉的Redis、Neo4j、ElasticSearch等
 * 如在构建RAG应用，必不可少
 *
 * @author weTeam
 * @since 2024/11/4
 **/
@SpringBootTest(classes = AiApp.class)
class VectorTest implements BaseMockApi {
    @Resource
    private VectorStore vectorStore;

    @Test
    void testVectorStore() {


// ...

        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

        vectorStore.add(documents);

// Retrieve documents similar to a query
        SearchRequest request = SearchRequest.query("Spring").withTopK(2);
        List<Document> results = this.vectorStore.similaritySearch(request);
        log.info("Similar documents: {}", results);
    }


}
