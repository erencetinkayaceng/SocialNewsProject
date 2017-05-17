package com.cruz.factory.Impl;

import java.util.List;

import com.cruz.factory.Search;
import com.cruz.service.UserService;

public class SearchUserImpl implements Search {

	private UserService userService;

	public SearchUserImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public List<?> doSearchByPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub

		return userService.doSearchFindAllByUsernameAndFullNameAndPage(searchWords, pageNum, pageSize);
	}

	@Override
	public int getTotalCount(String searchWords, int totalCount) {
		// TODO Auto-generated method stub
		if (totalCount == 0) {
			totalCount = userService.doSearchFindAllByUsernameAndFullName(searchWords).size();
		}
		return totalCount;
	}

}
