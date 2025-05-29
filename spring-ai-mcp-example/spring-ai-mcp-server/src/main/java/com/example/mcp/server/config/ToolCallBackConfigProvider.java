package com.example.mcp.server.config;

import com.example.mcp.server.mcp.McpServiceImpl;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallBackConfigProvider {
	@Bean
	public ToolCallbackProvider getMcpServiceProvider(McpServiceImpl mcpService) {
		return MethodToolCallbackProvider.builder().toolObjects(mcpService).build();
	}
}
