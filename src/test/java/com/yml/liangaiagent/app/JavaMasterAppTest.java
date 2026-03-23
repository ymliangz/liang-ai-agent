package com.yml.liangaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JavaMasterAppTest {

    @Resource
    JavaMasterApp javaMasterApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        String answer = javaMasterApp.doChat("初级程序员需要学什么", chatId);
        Assertions.assertNotNull(answer);
//        answer = javaMasterApp.doChat("我需要学AI agent开发吗", chatId);
//        Assertions.assertNotNull(answer);
//        answer = javaMasterApp.doChat("我是谁", chatId);
//        Assertions.assertNotNull(answer);
    }
}