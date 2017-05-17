package com.cruz.strategy.Impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.cruz.model.Post;
import com.cruz.service.StrategyMainPostService;
import com.cruz.strategy.StrategyMainPost;

public class StrategyFollowImpl implements StrategyMainPost {

	private StrategyMainPostService strategyMainPostService;

	public StrategyFollowImpl(StrategyMainPostService strategyMainPostService) {
		this.strategyMainPostService = strategyMainPostService;
	}

	@Override
	public List<Post> fecthAllPostByStrategyType(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return strategyMainPostService.fetchAllByStrategyFollowAndPage(getPrincipal(), pageNum, pageSize);
	}

	@Override
	public int getTotalCount(int totalCount) {
		// TODO Auto-generated method stub
		if (totalCount == 0) {
			totalCount = strategyMainPostService.fetchAllByStrategyFollow(getPrincipal()).size();
		}
		return totalCount;
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
