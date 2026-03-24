package com.yml.liangaiagent.app;

import com.yml.liangaiagent.advisor.MyLogAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 结构化输出
 */
@Component
public class EntertainmentMasterApp {
    private final ChatModel chatModel;

    public EntertainmentMasterApp(ChatModel dashScopeChatModel) {
        this.chatModel = dashScopeChatModel;
    }

    record ActorsFilms(String actor, List<String> movies) {
    }

    public void doChat() {
        ActorsFilms actorsFilms = ChatClient.create(chatModel).prompt()
                .user(u -> u.text("Generate the filmography of 5 movies for {actor}.")
                        .param("actor", "Tom Hanks"))
                .advisors(new MyLogAdvisor())
                .call()
                .entity(ActorsFilms.class);
    }
}
