package com.cruz.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.CommentRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.Comment;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public Comment saveComment(Comment comment) {
		// TODO Auto-generated method stub
		this.entityManager.persist(comment);
		return comment;
	}

	@Override
	public void deleteComment(Comment comment) {
		// TODO Auto-generated method stub

		if (this.entityManager.contains(comment)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			comment.setEnabled(!comment.isEnabled());
			updateComment(comment);
		} else {
			Comment deleteComment = findComment(comment.getId());
			deleteComment.setEnabled(!deleteComment.isEnabled());
			updateComment(deleteComment);
		}

	}

	@Override
	public Comment updateComment(Comment comment) {
		// TODO Auto-generated method stub

		Comment updatedComment = entityManager.merge(comment);
		this.entityManager.flush();

		return updatedComment;

	}

	@Override
	public Comment findComment(Long commentID) {
		// TODO Auto-generated method stub

		if (commentID == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		try {
			return this.entityManager.find(Comment.class, commentID);
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Comment> findAllCommentByPostLink(String postLink) {
		// TODO Auto-generated method stub
		if (postLink == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		TypedQuery<Comment> typedQuery = this.entityManager.createNamedQuery("Comment.findAllCommentByPostLink",
				Comment.class);
		try {
			return typedQuery.setParameter("postLink", postLink).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Comment> findAllCommentByPostLinkAndPage(String postLink, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (postLink == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		TypedQuery<Comment> typedQuery = this.entityManager.createNamedQuery("Comment.findAllCommentByPostLinkAndPage",
				Comment.class);
		try {
			return typedQuery.setParameter("postLink", postLink).setFirstResult(pageNum).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
