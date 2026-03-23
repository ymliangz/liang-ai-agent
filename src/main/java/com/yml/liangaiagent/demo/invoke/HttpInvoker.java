package com.yml.liangaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class HttpInvoker {

    public static String callWithHutool(String apiKey, String model, String systemContent, String userContent) {
        // API地址
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
        
        // 构建请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + apiKey);
        headers.put("Content-Type", "application/json");

        // 构建请求体
        JSONObject requestBody = JSONUtil.createObj();
        requestBody.set("model", model);

        JSONObject input = JSONUtil.createObj();
        JSONArray messages = JSONUtil.createArray();

        // 添加system消息
        JSONObject systemMsg = JSONUtil.createObj();
        systemMsg.set("role", "system");
        systemMsg.set("content", systemContent);
        messages.add(systemMsg);

        // 添加user消息
        JSONObject userMsg = JSONUtil.createObj();
        userMsg.set("role", "user");
        userMsg.set("content", userContent);
        messages.add(userMsg);

        input.set("messages", messages);
        requestBody.set("input", input);

        JSONObject parameters = JSONUtil.createObj();
        parameters.set("result_format", "message");
        requestBody.set("parameters", parameters);
        
        // 发送POST请求
        HttpResponse response = HttpRequest.post(url)
                .addHeaders(headers)
                .body(requestBody.toString())
                .execute();
        
        // 处理响应
        if (response.getStatus() == 200) {
            JSONObject responseBody = JSONUtil.parseObj(response.body());
            return responseBody.getJSONObject("output")
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getStr("content");
        } else {
            return "请求失败: " + response.body();
        }
    }
    
    public static void main(String[] args) {
        String apiKey = System.getenv("DASHSCOPE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("请设置环境变量 DASHSCOPE_API_KEY");
            System.exit(1);
        }
        
        String result = callWithHutool(
                apiKey,
                "qwen-plus",
                "You are a helpful assistant.",
                "你是谁？"
        );
        
        System.out.println("响应结果:");
        System.out.println(result);
    }
}