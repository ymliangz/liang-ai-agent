package com.yml.liangaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntertainmentMasterAppTest {

    @Resource
    EntertainmentMasterApp entertainmentMasterApp;

    @Test
    void doChat() {
        entertainmentMasterApp.doChat();
    }
}