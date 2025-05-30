package com.seaflower.example.chatMemory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
@RequestMapping("/chatMemory")
public class ChatMemoryController {
	private final ChatClient chatClient;

	public ChatMemoryController(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(new InMemoryChatMemory()).build()).build();
	}

	@GetMapping("/askInfoInMemory")
	public String askInfo(@RequestParam("userInput") String userInput, @RequestParam("chatId") String chatId) {
		String  callRecord = chatClient.prompt()
			.advisors(x -> x.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
				.param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
			.user(userInput)
			.call()
			.content();
		return callRecord;
	}
}
