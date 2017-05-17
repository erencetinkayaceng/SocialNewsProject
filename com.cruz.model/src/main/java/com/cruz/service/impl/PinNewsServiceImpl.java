package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.PinNewsRepository;
import com.cruz.model.PinNews;
import com.cruz.service.PinNewsService;

@Service("PinNewsService")
@Transactional
public class PinNewsServiceImpl implements PinNewsService {

	@Autowired
	PinNewsRepository pinNewsRepository;

	@Override
	public PinNews savePinNews(PinNews pinNews) {
		// TODO Auto-generated method stub
		return pinNewsRepository.savePinNews(pinNews);
	}

	@Override
	public void deletePinNews(PinNews pinNews) {
		// TODO Auto-generated method stub
		pinNewsRepository.deletePinNews(pinNews);
	}

	@Override
	public PinNews updatePinNews(PinNews pinNews) {
		// TODO Auto-generated method stub
		return pinNewsRepository.updatePinNews(pinNews);
	}

	@Override
	public PinNews findPinNewsById(Long id) {
		// TODO Auto-generated method stub
		return pinNewsRepository.findPinNewsById(id);
	}

	@Override
	public PinNews findPinNewsByPostIdAndUsername(Long postid, String username) {
		// TODO Auto-generated method stub
		return pinNewsRepository.findPinNewsByPostIdAndUsername(postid, username);
	}

	@Override
	public List<PinNews> findAllPinNewsByUsername(String username) {
		// TODO Auto-generated method stub
		return pinNewsRepository.findAllPinNewsByUsername(username);
	}

	@Override
	public List<PinNews> findAllPinNewsByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return pinNewsRepository.findAllPinNewsByUsernameAndPage(username, pageNum, pageSize);
	}

}
