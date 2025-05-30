package com.example.mcp.server.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

	@Tool(description = "列出个人信息")
	@Override
	public String listMyInfo(@ToolParam(description = "姓名", required = true) String userName) {
		if (Objects.equals(userName, "seaflower")) {
			return """
    			一个正直的，聪明的人。
				""";
		}

		if (Objects.equals(userName, "xiongyuhang")) {
			return """
    			一个宅男, 爱喝汽水的二次元男孩。
				""";
		}
		return null;
	}
}
