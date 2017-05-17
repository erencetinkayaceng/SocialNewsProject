package com.cruz.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.LikeRepository;
import com.cruz.model.Like;
import com.cruz.model.user.User;

@Repository
@Transactional
public class LikeRepositoryImpl implements LikeRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public Like saveLike(Like like) {
		// TODO Auto-generated method stub
		this.entityManager.persist(like);
		return like;
	}

	@Override
	public void deleteLike(Like like) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(like)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			like.setEnabled(!like.isEnabled());
			updateLike(like);
		} else {
			Like deleteLike = findLikeByLikeID(like.getId());
			deleteLike.setEnabled(!deleteLike.isEnabled());
			updateLike(deleteLike);
		}
	}

	@Override
	public Like updateLike(Like like) {
		// TODO Auto-generated method stub
		Like updateLike = entityManager.merge(like);
		this.entityManager.flush();

		return updateLike;
	}

	@Override
	public Like findLikeByLikeID(Long likeID) {
		// TODO Auto-generated method stub
		if (likeID == null) {
			return null;
		}

		try {
			return this.entityManager.find(Like.class, likeID);
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public Like findLikeByPostID(Long postID) {
		// TODO Auto-generated method stub
		TypedQuery<Like> typedQuery = this.entityManager.createNamedQuery("Like.findLikeByPostID", Like.class);
		try {
			return typedQuery.setParameter("postID", postID).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public Like findLikeByPostLinkAndUsername(String postLink, String username) {
		// TODO Auto-generated method stub
		TypedQuery<Like> typedQuery = this.entityManager.createNamedQuery("Like.findLikeByPostLinkAndUsername",
				Like.class);
		try {
			return typedQuery.setParameter("postLink", postLink).setParameter("username", username).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public long countLikesByPostID(Long postID) {
		// TODO Auto-generated method stub
		TypedQuery<User> typedQuery = this.entityManager.createNamedQuery("Like.countLikesByPostID", User.class);
		try {
			return typedQuery.setParameter("postID", postID).getResultList().size();
		} catch (NoResultException ex) {
			return 0;
		}
	}

	@Override
	public List<User> likeAllUserByPostID(Long postID) {
		// TODO Auto-generated method stub
		TypedQuery<User> typedQuery = this.entityManager.createNamedQuery("Like.likeAllUserByPostID", User.class);
		try {
			return typedQuery.setParameter("postID", postID).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
