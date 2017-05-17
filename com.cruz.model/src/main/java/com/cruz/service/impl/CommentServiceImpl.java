package com.cruz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.CommentRepository;
import com.cruz.model.Comment;
import com.cruz.service.CommentService;

@Service("CommentService")
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired(required = true)
	CommentRepository commentRepository;

	@Override
	public Comment saveComment(Comment comment) {
		// TODO Auto-generated method stub
		return commentRepository.saveComment(comment);
	}

	@Override
	public void deleteComment(Comment comment) {
		// TODO Auto-generated method stub
		commentRepository.deleteComment(comment);
	}

	@Override
	public Comment updateComment(Comment comment) {
		// TODO Auto-generated method stub
		return commentRepository.updateComment(comment);
	}

	@Override
	public Comment findComment(Long id) {
		// TODO Auto-generated method stub
		return commentRepository.findComment(id);
	}

	@Override
	public List<Comment> findAllCommentByPostLink(String postLink) {
		// TODO Auto-generated method stub
		return commentRepository.findAllCommentByPostLink(postLink);
	}

	@Override
	public List<Comment> findAllCommentByPostLinkAndPage(String postLink, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return commentRepository.findAllCommentByPostLinkAndPage(postLink, pageNum, pageSize);
	}

}
