package com.cruz.strategy;

import java.util.List;

import com.cruz.model.Post;

public interface StrategyMainPost {

	List<Post> fecthAllPostByStrategyType(int pageNum, int pageSize);

	int getTotalCount(int totalCount);
}
