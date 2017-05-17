package com.cruz.service;

import java.util.List;

import com.cruz.model.PinNews;

public interface PinNewsService {
	PinNews savePinNews(PinNews pinNews);

	void deletePinNews(PinNews pinNews);

	PinNews updatePinNews(PinNews pinNews);

	PinNews findPinNewsById(Long id);

	PinNews findPinNewsByPostIdAndUsername(Long postid, String username);

	List<PinNews> findAllPinNewsByUsername(String username);

	List<PinNews> findAllPinNewsByUsernameAndPage(String username, int pageNum, int pageSize);
}
