package com.cruz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.ImageRepository;
import com.cruz.model.Image;
import com.cruz.service.ImageService;

@Service("ImageService")
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;

	@Override
	public Image saveImage(Image image) {
		// TODO Auto-generated method stub
		return imageRepository.saveImage(image);
	}

	@Override
	public void deleteImage(Image image) {
		// TODO Auto-generated method stub
		imageRepository.deleteImage(image);
	}

	@Override
	public Image updateImage(Image image) {
		// TODO Auto-generated method stub
		return imageRepository.updateImage(image);
	}

	@Override
	public Image findImage(Long imageID) {
		// TODO Auto-generated method stub
		return imageRepository.findImage(imageID);
	}

}
