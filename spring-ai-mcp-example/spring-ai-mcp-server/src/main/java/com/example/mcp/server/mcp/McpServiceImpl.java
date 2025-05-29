package com.example.mcp.server.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service("mcpService")
public class McpServiceImpl implements IMcpService{
	@Tool(description = "我最喜欢的歌手")
	@Override
	public String getFavoriteSinger() {
		return "许嵩";
	}

	@Tool(description = "我最喜欢的导演")
	@Override
	public String getFavoriteDirector() {
		return "姜文";
	}
}
