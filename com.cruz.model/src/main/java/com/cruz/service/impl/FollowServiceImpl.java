package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.FollowRepository;
import com.cruz.model.Follow;
import com.cruz.service.FollowService;

@Service("FollowService")
@Transactional
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowRepository followRepository;

	@Override
	public Follow saveFollow(Follow follow) {
		// TODO Auto-generated method stub
		return followRepository.saveFollow(follow);
	}

	@Override
	public void deleteFollow(Follow follow) {
		// TODO Auto-generated method stub
		followRepository.deleteFollow(follow);
	}

	@Override
	public Follow updateFollow(Follow follow) {
		// TODO Auto-generated method stub
		return followRepository.updateFollow(follow);
	}

	@Override
	public Follow findFollow(String fer, String fed) {
		// TODO Auto-generated method stub
		return followRepository.findFollow(fer, fed);
	}

	@Override
	public List<Follow> findAllFollowerByUsername(String username) {
		// TODO Auto-generated method stub
		return followRepository.findAllFollowerByUsername(username);
	}

	@Override
	public List<Follow> findAllFollowedByUsername(String username) {
		// TODO Auto-generated method stub
		return followRepository.findAllFollowedByUsername(username);
	}

	@Override
	public List<Follow> findAllFollowerByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return followRepository.findAllFollowerByUsernameAndPage(username, pageNum, pageSize);
	}

	@Override
	public List<Follow> findAllFollowedByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return followRepository.findAllFollowedByUsernameAndPage(username, pageNum, pageSize);
	}

}
