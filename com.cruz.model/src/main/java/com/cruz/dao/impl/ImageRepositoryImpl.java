package com.cruz.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.ImageRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.Image;

@Repository
@Transactional
public class ImageRepositoryImpl implements ImageRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;
	
	@Override
	public Image saveImage(Image image) {
		// TODO Auto-generated method stub
		this.entityManager.persist(image);
		return image;
	}

	@Override
	public void deleteImage(Image image) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(image)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			image.setEnabled(!image.isEnabled());
			updateImage(image);
		} else {
			Image deleteImage = findImage(image.getId());
			deleteImage.setEnabled(!deleteImage.isEnabled());
			updateImage(deleteImage);
		}
	}

	@Override
	public Image updateImage(Image image) {
		// TODO Auto-generated method stub
		Image updateImage = entityManager.merge(image);
		this.entityManager.flush();

		return updateImage;
	}

	@Override
	public Image findImage(Long imageID) {
		// TODO Auto-generated method stub
		if (imageID == null){
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		try {
			return this.entityManager.find(Image.class, imageID);
		} catch (NoResultException ex) {
			return null;
		}
	}

}
