package com.cruz.strategy.Impl;

import java.util.List;

import com.cruz.model.Post;
import com.cruz.service.StrategyMainPostService;
import com.cruz.strategy.StrategyMainPost;

public class StrategyPopularPostImpl implements StrategyMainPost {

	private StrategyMainPostService strategyMainPostService;

	public StrategyPopularPostImpl(StrategyMainPostService strategyMainPostService) {
		this.strategyMainPostService = strategyMainPostService;
	}

	@Override
	public List<Post> fecthAllPostByStrategyType(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return strategyMainPostService.fetchAllByStrategyPopularPost(pageNum, pageSize);
	}

	@Override
	public int getTotalCount(int totalCount) {
		// TODO Auto-generated method stub
		if (totalCount == 0) {
			totalCount = strategyMainPostService.fetchAllByStrategyPopularPost().size();
		}
		return totalCount;
	}
}
