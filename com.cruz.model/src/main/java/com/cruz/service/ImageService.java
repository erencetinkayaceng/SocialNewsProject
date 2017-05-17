package com.cruz.service;

import com.cruz.model.Image;

public interface ImageService {
	Image saveImage(Image image);

	void deleteImage(Image image);

	Image updateImage(Image image);

	Image findImage(Long imageID);

}
