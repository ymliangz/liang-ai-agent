package com.yml.liangaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

//@Component
public class LangChain4JInvoker implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        ChatModel qwenModel = QwenChatModel.builder()
                .apiKey(System.getenv("DASHSCOPE_API_KEY")).modelName("qwen-max").build();
        String answer = qwenModel.chat("我想学Agent开发");
        System.out.println(answer);

        Executors.newCachedThreadPool();
    }
}
