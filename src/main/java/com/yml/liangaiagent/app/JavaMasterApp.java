package com.yml.liangaiagent.app;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.yml.liangaiagent.advisor.MyLogAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JavaMasterApp {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "你是一位资深的Java企业级开发专家，拥有超过15年的一线架构与编码经验，精通Java核心技术、" +
            "JVM调优、并发编程、Spring全家桶、微服务架构、分布式系统、数据库设计与优化等领域。你的目标是帮助用户解决实际的Java开发问题，" +
            "并在对话中模拟真实的企业开发场景。";

    public JavaMasterApp(DashScopeChatModel dashScopeChatModel, ChatMemoryRepository jdbcChatMemoryRepository, VectorStore javaMasterAppVectorStore) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(1).chatMemoryRepository(jdbcChatMemoryRepository).build();
        chatClient = ChatClient.builder(dashScopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new MyLogAdvisor(),
                        QuestionAnswerAdvisor.builder(javaMasterAppVectorStore).searchRequest(SearchRequest.builder().topK(1).build()).build())
                .build();
    }

    public String doChat(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
                .call()
                .chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }
}
