package com.cruz.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cruz.model.Post;

@Component
public class StrategyContext {

	private StrategyMainPost strategy;

	public void setStrategyType(StrategyMainPost strategy) {
		this.strategy = strategy;
	}

	public List<Post> fecthAllPostByStrategyType(int pageNum, int pageSize) {
		return strategy.fecthAllPostByStrategyType(pageNum, pageSize);
	}

	public int getTotalCount(int totalCount) {
		return strategy.getTotalCount(totalCount);
	}

}
