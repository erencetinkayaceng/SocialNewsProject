package com.cruz.service;

import com.cruz.model.Video;

public interface VideoService {
	Video saveVideo(Video video);

	void deleteVideo(Video video);

	Video updateVideo(Video video);

	Video findVideo(Long videoID);
}
