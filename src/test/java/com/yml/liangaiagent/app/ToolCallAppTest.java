package com.yml.liangaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToolCallAppTest {

    @Resource
    ToolCallApp toolCallApp;

    @Test
    void doChat() {
        String s = toolCallApp.doChat();
        Assertions.assertNotNull(s);
    }
}