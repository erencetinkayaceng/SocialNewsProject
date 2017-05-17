package com.cruz.dao;

import java.util.List;

import com.cruz.model.ReservedNews;

public interface ReservedNewsRepository {
	ReservedNews saveReservedNews(ReservedNews reservedNews);

	void deleteReservedNews(ReservedNews reservedNews);

	ReservedNews updateReservedNews(ReservedNews reservedNews);

	ReservedNews findReservedNewsById(Long id);

	ReservedNews findReservedNewsByPostIdAndUsername(Long postid, String username);

	List<ReservedNews> findAllReservedNewsByUsername(String username);

	List<ReservedNews> findAllReservedNewsByUsernameAndPage(String username, int pageNum, int pageSize);
}
