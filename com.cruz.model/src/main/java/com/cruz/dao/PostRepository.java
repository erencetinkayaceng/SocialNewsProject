package com.cruz.dao;

import java.util.List;

import com.cruz.model.Post;

public interface PostRepository {
	Post savePost(Post post);

	void deletePost(Post post);

	Post updatePost(Post post);

	Post findPostByPostID(Long postID);

	Post findPostByUserID(Long userID);

	Post findPostByPostLink(String postLink);

	List<Post> findAllPostByUsernameAndPostType(String username, String postType);

	List<Post> findAllPostByUsernameAndPage(String username, int pageNum, int pageSize, String postType,
			String updateOrderBy);

	List<Post> doSearchFindAllByTitleAndContent(String searchWords);

	List<Post> doSearchFindAllByTitleAndContentAndPage(String searchWords, int pageNum, int pageSize);

	List<Post> doSearchFindAllByHashTag(String searchWords);

	List<Post> doSearchFindAllByHashTagAndPage(String searchWords, int pageNum, int pageSize);

	List<String> doSearchFindAllPopulerByHashTag();

	List<String> AllPostType();
}
