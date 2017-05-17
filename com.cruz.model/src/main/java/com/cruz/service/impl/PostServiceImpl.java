package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.PostRepository;
import com.cruz.model.Post;
import com.cruz.service.PostService;

@Service("PostService")
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;

	@Override
	public Post savePost(Post post) {
		// TODO Auto-generated method stub
		postRepository.savePost(post);
		return post;
	}

	@Override
	public void deletePost(Post post) {
		// TODO Auto-generated method stub
		postRepository.deletePost(post);
	}

	@Override
	public Post updatePost(Post post) {
		// TODO Auto-generated method stub
		return postRepository.updatePost(post);
	}

	@Override
	public Post findPostByPostID(Long postID) {
		// TODO Auto-generated method stub
		return postRepository.findPostByPostID(postID);
	}

	@Override
	public Post findPostByUserID(Long userID) {
		// TODO Auto-generated method stub
		return postRepository.findPostByUserID(userID);
	}

	@Override
	public List<Post> findAllPostByUsernameAndPostType(String username, String postType) {
		// TODO Auto-generated method stub
		return postRepository.findAllPostByUsernameAndPostType(username, postType);
	}

	@Override
	public List<String> AllPostType() {
		// TODO Auto-generated method stub
		return postRepository.AllPostType();
	}

	@Override
	public Post findPostByPostLink(String postLink) {
		// TODO Auto-generated method stub
		return postRepository.findPostByPostLink(postLink);
	}

	@Override
	public List<Post> findAllPostByUsernameAndPage(String username, int pageNum, int pageSize, String postType,
			String updateOrderBy) {
		// TODO Auto-generated method stub
		return postRepository.findAllPostByUsernameAndPage(username, pageNum, pageSize, postType, updateOrderBy);
	}

	@Override
	public List<Post> doSearchFindAllByTitleAndContent(String searchWords) {
		// TODO Auto-generated method stub
		return postRepository.doSearchFindAllByTitleAndContent(searchWords);
	}

	@Override
	public List<Post> doSearchFindAllByTitleAndContentAndPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return postRepository.doSearchFindAllByTitleAndContentAndPage(searchWords, pageNum, pageSize);
	}

	@Override
	public List<Post> doSearchFindAllByHashTag(String searchWords) {
		// TODO Auto-generated method stub
		return postRepository.doSearchFindAllByHashTag(searchWords);
	}

	@Override
	public List<Post> doSearchFindAllByHashTagAndPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return postRepository.doSearchFindAllByHashTagAndPage(searchWords, pageNum, pageSize);
	}

	@Override
	public List<String> doSearchFindAllPopulerByHashTag() {
		// TODO Auto-generated method stub
		return postRepository.doSearchFindAllPopulerByHashTag();
	}
}
