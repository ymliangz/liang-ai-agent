package com.yml.liangaiagent.demo.invoke;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SpringAiInvoker implements CommandLineRunner {

    private final ChatClient client;

    public SpringAiInvoker(ChatClient.Builder chatClientBuilder) {
        this.client = chatClientBuilder.build();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.client.prompt()
                .user("你好，你是谁")
                .call()
                .content());
    }
}
