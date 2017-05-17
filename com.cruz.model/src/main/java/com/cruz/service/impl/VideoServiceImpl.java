package com.cruz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.VideoRepository;
import com.cruz.model.Video;
import com.cruz.service.VideoService;

@Service("VideoService")
@Transactional
public class VideoServiceImpl implements VideoService {

	@Autowired
	VideoRepository videoRepository;
	
	@Override
	public Video saveVideo(Video video) {
		// TODO Auto-generated method stub
		return videoRepository.saveVideo(video);
	}

	@Override
	public void deleteVideo(Video video) {
		// TODO Auto-generated method stub
		videoRepository.deleteVideo(video);
	}

	@Override
	public Video updateVideo(Video video) {
		// TODO Auto-generated method stub
		return videoRepository.updateVideo(video);
	}

	@Override
	public Video findVideo(Long videoID) {
		// TODO Auto-generated method stub
		return videoRepository.findVideo(videoID);
	}

}
