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

import com.cruz.dao.FollowRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.Follow;

@Repository
@Transactional
public class FollowRepositoryImpl implements FollowRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public Follow saveFollow(Follow follow) {
		// TODO Auto-generated method stub
		this.entityManager.persist(follow);
		return follow;
	}

	@Override
	public void deleteFollow(Follow follow) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(follow)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			follow.setEnabled(!follow.isEnabled());
			updateFollow(follow);
		} else {
			Follow deleteFollow = findFollow(follow.getFollower().getUsername(), follow.getFollowed().getUsername());
			deleteFollow.setEnabled(!deleteFollow.isEnabled());
			updateFollow(deleteFollow);
		}
	}

	@Override
	public Follow updateFollow(Follow follow) {
		// TODO Auto-generated method stub

		Follow updateFollow = entityManager.merge(follow);
		this.entityManager.flush();

		return updateFollow;

	}

	@Override
	public Follow findFollow(String fer, String fed) {
		// TODO Auto-generated method stub
		if ((fer == null) || (fed == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Follow> typedQuery = this.entityManager.createNamedQuery("Follow.findFollowerByUsername",
				Follow.class);
		try {
			return typedQuery.setParameter("fer", fer).setParameter("fed", fed).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}

	}

	@Override
	public List<Follow> findAllFollowerByUsername(String username) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Follow> typedQuery = this.entityManager.createNamedQuery("Follow.findAllFollowerByUsername",
				Follow.class);
		try {
			return typedQuery.setParameter("followed", username).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Follow> findAllFollowedByUsername(String username) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Follow> typedQuery = this.entityManager.createNamedQuery("Follow.findAllFollowedByUsername",
				Follow.class);
		try {
			return typedQuery.setParameter("follower", username).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Follow> findAllFollowerByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Follow> typedQuery = this.entityManager.createNamedQuery("Follow.findAllFollowerByUsernameAndPage",
				Follow.class);
		try {
			return typedQuery.setParameter("followed", username).setFirstResult(pageNum).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Follow> findAllFollowedByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Follow> typedQuery = this.entityManager.createNamedQuery("Follow.findAllFollowedByUsernameAndPage",
				Follow.class);
		try {
			return typedQuery.setParameter("follower", username).setFirstResult(pageNum).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
