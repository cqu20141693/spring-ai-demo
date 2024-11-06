package com.witeam.ai.model;

import com.witeam.ai.AiApp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AiApp.class)
@Slf4j
class HighSchoolMathModelServiceTest {

    @Resource
    private HighSchoolMathModelService highSchoolMathModelService;

    @Test
    void testGeneration() {
        String output = highSchoolMathModelService.generation("什么是复数");
        log.info(output);
        assertTrue(output.contains("复数"));
    }

}
