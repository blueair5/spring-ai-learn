package com.seaflower.example.chatClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("/chatClient")
public class ChatClientController {
	private final ChatClient chatClient;

	public ChatClientController(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
	}

	/**
	 * String 聊天
	 * @param userInput
	 * @return
	 */
	@GetMapping("/ai")
	String generation(String userInput) {
		return this.chatClient.prompt()
			.user(userInput)
			.call()
			.content();
	}

	/**
	 * 获取聊天响应
	 * @param userInput 用户输入
	 * @return ChatResponse 对象
	 */
	@GetMapping("/chatResponse")
	public ChatResponse chatResponse(@RequestParam("userInput") String userInput) {
		ChatResponse chatResponse = chatClient.prompt()
			.user(userInput)
			.call()
			.chatResponse();
		return chatResponse;
	}

	/**
	 * 返回一个实体。
	 *
	 * <p>如何实现的？</p>
	 * 具体的实现方案在 {@link BeanOutputConverter#generateSchema()}，将 Bean 转换成 JSON Schema，
	 * 同时将 JSON Schema 提供给 AI，并严格限制这个模型的输出格式，让 AI 生成符合 JSON Schema 的内容。
	 * 然后将返回的内容通过 Jackson 转换成实体类。
	 *
	 * <p>可以看出，合适的 prompt 是很关键的，通过合适的 prompt 可以生成如下数据：</p>
	 * <pre>
	 * {
	 *   "actor": "姜文",
	 *   "movies": ["阳光灿烂的日子", "鬼子来了", "让子弹飞"]
	 * }
	 * </pre>
	 *
	 * @return 返回生成的实体类
	 */
	@GetMapping("/entiyResponse")
	public String entiyResponse() throws JsonProcessingException {
		ActorFilms actorFilms = chatClient.prompt()
			.user("生成周星驰的电影作品")
			.call()
			.entity(ActorFilms.class);

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(actorFilms);
	}

	/**
	 * 返回一个实体, 增加一个自定义的 advisor
	 * @return
	 */
	@GetMapping("/advisorUser")
	public String advisorUse() throws JsonProcessingException {
		ActorFilms actorFilms = chatClient.prompt()
			.advisors(new MyCustomAdvisor())
			.user("列出我喜欢的一个导演的作品")
			.call()
			.entity(ActorFilms.class);

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(actorFilms);
	}

	record ActorFilms(String actor, List<String> movies) {}

}
