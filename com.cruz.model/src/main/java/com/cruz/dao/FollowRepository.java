package com.cruz.dao;

import java.util.List;

import com.cruz.model.Follow;

public interface FollowRepository {
	Follow saveFollow(Follow follow);

	void deleteFollow(Follow follow);

	Follow updateFollow(Follow follow);

	Follow findFollow(String fer, String fed);

	List<Follow> findAllFollowerByUsername(String username);

	List<Follow> findAllFollowerByUsernameAndPage(String username, int pageNum, int pageSize);

	List<Follow> findAllFollowedByUsername(String username);

	List<Follow> findAllFollowedByUsernameAndPage(String username, int pageNum, int pageSize);
}
