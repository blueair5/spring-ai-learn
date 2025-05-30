package com.example.mcp.client.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
public class McpController {
	@Autowired
	private OpenAiChatModel openAiChatModel;

	@Autowired
	private ToolCallbackProvider toolCallbackProvider;

	@GetMapping("/ai/generate")
	public String generate(@RequestParam(value = "message", defaultValue = "最喜欢的歌手") String message,
						   @RequestParam("chatId") String chatId) {
		ChatClient chatClient = ChatClient.builder(openAiChatModel)
			.defaultTools(toolCallbackProvider.getToolCallbacks())
			.defaultAdvisors(MessageChatMemoryAdvisor.builder(new InMemoryChatMemory()).build())
			.build();
		if (StringUtils.isEmpty(chatId)) {
			chatId = "112233";
		}
		String finalChatId = chatId;
		return chatClient.prompt()
			.advisors(x -> x.param(CHAT_MEMORY_CONVERSATION_ID_KEY, finalChatId)
				.param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
			.user(message).call().content();
	}
}
