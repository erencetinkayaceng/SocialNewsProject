package com.cruz.service;

import java.util.List;

import com.cruz.model.Post;

public interface StrategyMainPostService {

	List<Post> fetchAllByStrategyFollow(String username);

	List<Post> fetchAllByStrategyFollowAndPage(String username, int pageNum, int pageSize);

	List<Post> fetchAllByStrategyPopularPost();

	List<Post> fetchAllByStrategyPopularPost(int pageNum, int pageSize);

}
