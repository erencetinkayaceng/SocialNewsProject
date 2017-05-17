package com.cruz.service;

import java.util.List;

import com.cruz.model.ReservedNews;

public interface ReservedNewsService {
	ReservedNews saveReservedNews(ReservedNews reservedNews);

	void deleteReservedNews(ReservedNews reservedNews);

	ReservedNews updateReservedNews(ReservedNews reservedNews);

	ReservedNews findReservedNewsById(Long id);

	ReservedNews findReservedNewsByPostIdAndUsername(Long postid, String username);

	List<ReservedNews> findAllReservedNewsByUsername(String username);

	List<ReservedNews> findAllReservedNewsByUsernameAndPage(String username, int pageNum, int pageSize);
}
