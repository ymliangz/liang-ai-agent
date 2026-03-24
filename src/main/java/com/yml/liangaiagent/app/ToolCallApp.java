package com.yml.liangaiagent.app;

import com.yml.liangaiagent.advisor.MyLogAdvisor;
import com.yml.liangaiagent.tool.AgentThinking;
import com.yml.liangaiagent.tool.DateTimeTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.augment.AugmentedToolCallbackProvider;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ToolCallApp {
    private final ChatModel chatModel;

    public ToolCallApp(ChatModel dashScopeChatModel) {
        this.chatModel = dashScopeChatModel;
    }

    public String doChat() {
        AugmentedToolCallbackProvider<AgentThinking> provider = AugmentedToolCallbackProvider
                .<AgentThinking>builder()
                .toolObject(new DateTimeTools())  // Your @Tool annotated class
                .argumentType(AgentThinking.class)
                .argumentConsumer(event -> {
                    AgentThinking thinking = event.arguments();
                    log.info("Tool: {} | Reasoning: {}", event.toolDefinition().name(), thinking.innerThought());
                })
                .removeExtraArgumentsAfterProcessing(true)
                .build();

        String answer = ChatClient.create(chatModel).prompt("What day is tomorrow? Set an alarm 10 minutes from now.").advisors(new MyLogAdvisor())
                .toolCallbacks(provider).call().content();
        System.out.println(answer);
//        ToolCallback toolCallback = FunctionToolCallback.builder("current weather", new WeatherService())
//                .description("Get the weather in location")
//                .inputType(WeatherService.WeatherRequest.class)
//                .build();
//        answer = ChatClient.create(chatModel).prompt("What's the weather like in Beijing?").advisors(new MyLogAdvisor())
//                .toolCallbacks(toolCallback).call().content();
//        System.out.println(answer);

        ChatClient client = ChatClient.builder(chatModel).defaultToolNames("currentWeather").build();
        answer = client.prompt().user("What's the weather like in Xi'an?").call().content();
        System.out.println(answer);
        return answer;
    }
}
