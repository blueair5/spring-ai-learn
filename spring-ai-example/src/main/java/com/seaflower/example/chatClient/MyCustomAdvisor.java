package com.seaflower.example.chatClient;

import lombok.Data;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.*;

import java.util.List;
import java.util.Map;

/**
 * advisor 可以在调用链中对请求和响应进行处理
 */

@Data
public class MyCustomAdvisor implements CallAroundAdvisor {

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
		String sysParam = "我喜欢的电影导演有: 姜文, 张艺谋";
		advisedRequest = AdvisedRequest.from(advisedRequest).userText(sysParam).build();
		AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
		return chain.nextAroundCall(advisedRequest);
	}

	@Override
	public String getName() {
		return "movie";
	}

	// 较低的值会先执行
	@Override
	public int getOrder() {
		return 100;
	}
}
