package com.cruz.dao;

import com.cruz.model.Video;

public interface VideoRepository {
	Video saveVideo(Video video);

	void deleteVideo(Video video);

	Video updateVideo(Video video);

	Video findVideo(Long videoID);
}
