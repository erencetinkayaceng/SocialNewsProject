package com.cruz.dao;

import java.util.List;

import com.cruz.model.user.User;

public interface UserRepository {
	User saveUser(User User);

	void deleteUser(User User);

	User updateUser(User User);

	User findUserByUserID(Long UserID);

	User findUserByUsername(String username);

	List<User> doSearchFindAllByUsernameAndFullName(String searchWords);

	List<User> doSearchFindAllByUsernameAndFullNameAndPage(String searchWords, int pageNum, int pageSize);
}
