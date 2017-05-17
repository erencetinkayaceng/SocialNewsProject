package com.cruz.dao;

import com.cruz.model.Image;

public interface ImageRepository {
	Image saveImage(Image image);

	void deleteImage(Image image);

	Image updateImage(Image image);

	Image findImage(Long imageID);

}
