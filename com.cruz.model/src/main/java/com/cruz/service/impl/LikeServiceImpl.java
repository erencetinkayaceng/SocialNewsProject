package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.LikeRepository;
import com.cruz.model.Like;
import com.cruz.model.user.User;
import com.cruz.service.LikeService;

@Service("LikeService")
@Transactional
public class LikeServiceImpl implements LikeService {

	@Autowired
	LikeRepository likeRepository;

	@Override
	public Like saveLike(Like like) {
		// TODO Auto-generated method stub
		return likeRepository.saveLike(like);
	}

	@Override
	public void deleteLike(Like like) {
		// TODO Auto-generated method stub
		likeRepository.deleteLike(like);
	}

	@Override
	public Like updateLike(Like like) {
		// TODO Auto-generated method stub
		return likeRepository.updateLike(like);
	}

	@Override
	public Like findLikeByLikeID(Long likeID) {
		// TODO Auto-generated method stub
		return likeRepository.findLikeByLikeID(likeID);
	}

	@Override
	public Like findLikeByPostID(Long postID) {
		// TODO Auto-generated method stub
		return likeRepository.findLikeByPostID(postID);
	}

	@Override
	public Like findLikeByPostLinkAndUsername(String postLink, String username) {
		// TODO Auto-generated method stub
		return likeRepository.findLikeByPostLinkAndUsername(postLink, username);
	}

	@Override
	public long countLikesByPostID(Long postID) {
		// TODO Auto-generated method stub
		return likeRepository.countLikesByPostID(postID);
	}

	@Override
	public List<User> likeAllUserByPostID(Long postID) {
		// TODO Auto-generated method stub
		return likeRepository.likeAllUserByPostID(postID);
	}

}
