package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.ReservedNewsRepository;
import com.cruz.model.ReservedNews;
import com.cruz.service.ReservedNewsService;

@Service("ReservedNewsService")
@Transactional
public class ReservedNewsServiceImpl implements ReservedNewsService {

	@Autowired
	ReservedNewsRepository reservedNewsRepository;

	@Override
	public ReservedNews saveReservedNews(ReservedNews reservedNews) {
		// TODO Auto-generated method stub
		return reservedNewsRepository.saveReservedNews(reservedNews);
	}

	@Override
	public void deleteReservedNews(ReservedNews reservedNews) {
		// TODO Auto-generated method stub
		reservedNewsRepository.deleteReservedNews(reservedNews);
	}

	@Override
	public ReservedNews updateReservedNews(ReservedNews reservedNews) {
		// TODO Auto-generated method stub
		return reservedNewsRepository.updateReservedNews(reservedNews);
	}

	@Override
	public ReservedNews findReservedNewsById(Long id) {
		// TODO Auto-generated method stub
		return reservedNewsRepository.findReservedNewsById(id);
	}

	@Override
	public ReservedNews findReservedNewsByPostIdAndUsername(Long postid, String username) {
		// TODO Auto-generated method stub
		return reservedNewsRepository.findReservedNewsByPostIdAndUsername(postid, username);
	}

	@Override
	public List<ReservedNews> findAllReservedNewsByUsername(String username) {
		// TODO Auto-generated method stub
		return reservedNewsRepository.findAllReservedNewsByUsername(username);
	}

	@Override
	public List<ReservedNews> findAllReservedNewsByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return reservedNewsRepository.findAllReservedNewsByUsernameAndPage(username, pageNum, pageSize);
	}

}
