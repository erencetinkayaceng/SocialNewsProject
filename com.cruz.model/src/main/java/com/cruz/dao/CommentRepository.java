package com.cruz.dao;

import java.util.List;

import com.cruz.model.Comment;

public interface CommentRepository {

	Comment saveComment(Comment comment);

	void deleteComment(Comment comment);

	Comment updateComment(Comment comment);

	Comment findComment(Long commentID);

	List<Comment> findAllCommentByPostLink(String postLink);

	List<Comment> findAllCommentByPostLinkAndPage(String postLink, int pageNum, int pageSize);
}
