package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.StrategyMainPostRepository;
import com.cruz.model.Post;

@Service("StrategyMainPostService")
@Transactional
public class StrategyMainPostService implements com.cruz.service.StrategyMainPostService {

	@Autowired
	StrategyMainPostRepository strategyMainPostRepository;

	@Override
	public List<Post> fetchAllByStrategyFollow(String username) {
		// TODO Auto-generated method stub
		return strategyMainPostRepository.fetchAllByStrategyFollow(username);
	}

	@Override
	public List<Post> fetchAllByStrategyFollowAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return strategyMainPostRepository.fetchAllByStrategyFollowAndPage(username, pageNum, pageSize);
	}

	@Override
	public List<Post> fetchAllByStrategyPopularPost() {
		// TODO Auto-generated method stub
		return strategyMainPostRepository.fetchAllByStrategyPopularPost();
	}

	@Override
	public List<Post> fetchAllByStrategyPopularPost(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return strategyMainPostRepository.fetchAllByStrategyPopularPost(pageNum, pageSize);
	}

}
