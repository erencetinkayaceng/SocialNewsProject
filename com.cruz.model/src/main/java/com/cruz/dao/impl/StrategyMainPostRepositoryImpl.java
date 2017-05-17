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

import com.cruz.dao.StrategyMainPostRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.Post;

@Repository
@Transactional
public class StrategyMainPostRepositoryImpl implements StrategyMainPostRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public List<Post> fetchAllByStrategyFollow(String username) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("StrategyMainPost.fetchAllByStrategyFollow",
				Post.class);
		try {
			return typedQuery.setParameter("username", username).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> fetchAllByStrategyFollowAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("StrategyMainPost.fetchAllByStrategyFollow",
				Post.class);
		try {
			return typedQuery.setParameter("username", username).setFirstResult(pageNum).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> fetchAllByStrategyPopularPost() {
		// TODO Auto-generated method stub
		TypedQuery<Post> typedQuery = this.entityManager
				.createNamedQuery("StrategyMainPost.fetchAllByStrategyPopularPost", Post.class);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> fetchAllByStrategyPopularPost(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		TypedQuery<Post> typedQuery = this.entityManager
				.createNamedQuery("StrategyMainPost.fetchAllByStrategyPopularPost", Post.class);
		try {
			return typedQuery.setFirstResult(pageNum).setMaxResults(pageSize).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
