package com.cruz.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.Video;

@Repository
@Transactional
public class VideoRepositoryImpl implements com.cruz.dao.VideoRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public Video saveVideo(Video video) {
		// TODO Auto-generated method stub
		this.entityManager.persist(video);
		return video;
	}

	@Override
	public void deleteVideo(Video video) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(video)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			video.setEnabled(!video.isEnabled());
			updateVideo(video);
		} else {
			Video deleteVideo = findVideo(video.getId());
			deleteVideo.setEnabled(!deleteVideo.isEnabled());
			updateVideo(deleteVideo);
		}
	}

	@Override
	public Video updateVideo(Video video) {
		// TODO Auto-generated method stub
		Video updatedVideo = entityManager.merge(video);
		this.entityManager.flush();

		return updatedVideo;
	}

	@Override
	public Video findVideo(Long videoID) {
		// TODO Auto-generated method stub
		if (videoID == null){
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		try {
			return this.entityManager.find(Video.class, videoID);
		} catch (NoResultException ex) {
			return null;
		}
	}

}
