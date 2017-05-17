package com.cruz.service;

import java.util.List;

import com.cruz.model.Like;
import com.cruz.model.user.User;

public interface LikeService {
	Like saveLike(Like like);

	void deleteLike(Like like);

	Like updateLike(Like like);

	Like findLikeByLikeID(Long likeID);

	Like findLikeByPostID(Long postID);

	Like findLikeByPostLinkAndUsername(String postLink, String username);

	long countLikesByPostID(Long postID);

	List<User> likeAllUserByPostID(Long postID);
}
