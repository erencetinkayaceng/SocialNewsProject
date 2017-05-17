package com.cruz.service;

import java.util.List;

import com.cruz.model.Comment;

public interface CommentService {

	Comment saveComment(Comment comment);

	void deleteComment(Comment comment);

	Comment updateComment(Comment comment);

	Comment findComment(Long id);

	List<Comment> findAllCommentByPostLink(String postLink);

	List<Comment> findAllCommentByPostLinkAndPage(String postLink, int pageNum, int pageSize);
}
