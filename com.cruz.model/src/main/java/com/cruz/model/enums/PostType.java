package com.cruz.model.enums;

public enum PostType {
	NORMAL("Normal"), HEADLINE("Manset"), DRAFT("Taslak");

	String postType;

	private PostType(String postType) {
		this.postType = postType;
	}

	public String getPostType() {
		return postType;
	}
}
