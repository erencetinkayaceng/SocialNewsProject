package com.cruz.factory.Impl;

import java.util.List;

import com.cruz.factory.Search;
import com.cruz.service.PostService;

public class SearchPostTitleAndContentImpl implements Search {

	private PostService postService;

	public SearchPostTitleAndContentImpl(PostService postService) {
		this.postService = postService;
	}

	@Override
	public List<?> doSearchByPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return postService.doSearchFindAllByTitleAndContentAndPage(searchWords, pageNum, pageSize);
	}

	@Override
	public int getTotalCount(String searchWords, int totalCount) {
		// TODO Auto-generated method stub
		if (totalCount == 0) {
			totalCount = postService.doSearchFindAllByTitleAndContent(searchWords).size();
		}
		return totalCount;
	}
}
