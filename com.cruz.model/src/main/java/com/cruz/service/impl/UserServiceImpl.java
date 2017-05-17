package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.UserRepository;
import com.cruz.model.user.User;
import com.cruz.service.UserService;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository UserRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User User) {
		// TODO Auto-generated method stub
		User.setPassword(passwordEncoder.encode(User.getPassword()));
		return UserRepository.saveUser(User);
	}

	@Override
	public void deleteUser(User User) {
		// TODO Auto-generated method stub
		UserRepository.deleteUser(User);
	}

	@Override
	public User updateUser(User User) {
		// TODO Auto-generated method stub
		return UserRepository.updateUser(User);
	}

	@Override
	public User findUserByUserID(Long UserID) {
		// TODO Auto-generated method stub
		return UserRepository.findUserByUserID(UserID);
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return UserRepository.findUserByUsername(username);
	}

	@Override
	public List<User> doSearchFindAllByUsernameAndFullName(String searchWords) {
		// TODO Auto-generated method stub
		return UserRepository.doSearchFindAllByUsernameAndFullName(searchWords);
	}

	@Override
	public List<User> doSearchFindAllByUsernameAndFullNameAndPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return UserRepository.doSearchFindAllByUsernameAndFullNameAndPage(searchWords, pageNum, pageSize);
	}

}
