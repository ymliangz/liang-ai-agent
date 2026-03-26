## AI Agent编程学习
基于Spring AI框架，学习了解AI agent开发的相关概念

## 项目介绍
这是一个基于Spring AI的AI Agent学习项目，展示了如何集成各种AI工具和MCP（Model Context Protocol）服务。

## Amap MCP 集成

### 功能描述
集成了高德地图的MCP服务，实现了查找附近加油站的功能。

### 配置步骤

1. **获取高德地图API Key**
   - 访问 [高德开放平台](https://lbs.amap.com/)
   - 注册账号并创建应用
   - 获取Web服务的API Key

2. **设置环境变量**
   ```bash
   # Windows
   set AMAP_API_KEY=你的高德地图API_Key

   # Linux/Mac
   export AMAP_API_KEY=你的高德地图API_Key
   ```

3. **或者在配置文件中直接设置**
   编辑 `src/main/resources/application-local.yml`，添加：
   ```yaml
   amap:
     mcp:
       api-key: 你的高德地图API_Key
   ```

### API 接口

#### 查找附近加油站
- **GET** `/api/amap/nearby-gas-stations`
  - 参数：
    - `latitude`: 纬度（必填）
    - `longitude`: 经度（必填）
    - `radius`: 搜索半径（米，默认1000）

- **POST** `/api/amap/nearby-gas-stations`
  - 请求体：
    ```json
    {
      "latitude": 39.90923,
      "longitude": 116.397428,
      "radius": 1000
    }
    ```

### AI 工具调用
工具名称：`findNearbyGasStations`

AI Agent可以通过Spring AI的工具调用机制使用此功能：
```java
// 示例：通过AI对话使用
ChatClient client = ChatClient.builder(chatModel)
    .defaultToolNames("findNearbyGasStations")
    .build();

String answer = client.prompt()
    .user("帮我查找北京市中心附近1000米内的加油站")
    .call()
    .content();
```

### 测试
运行测试类：
```bash
mvn test -Dtest=AmapServiceTest
```

## 技术栈
- Spring Boot 3.5.11
- Spring AI 1.1.3
- 高德地图API
- MCP (Model Context Protocol)
