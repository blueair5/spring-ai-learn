package com.example.mcp.client.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class McpController {
	@Autowired
	private OpenAiChatModel openAiChatModel;

	@Autowired
	private ToolCallbackProvider toolCallbackProvider;

	@GetMapping("/ai/generate")
	public String generate(@RequestParam(value = "message", defaultValue = "最喜欢的歌手") String message) {
		ChatClient chatClient = ChatClient.builder(openAiChatModel)
			.defaultTools(toolCallbackProvider.getToolCallbacks())
			.build();
		ChatClient.CallResponseSpec call = chatClient.prompt(message).call();
		String content = call.content();
		return content;
	}
}
