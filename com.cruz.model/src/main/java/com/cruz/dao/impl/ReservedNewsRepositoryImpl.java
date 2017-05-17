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

import com.cruz.dao.ReservedNewsRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.ReservedNews;

@Repository
@Transactional
public class ReservedNewsRepositoryImpl implements ReservedNewsRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public ReservedNews saveReservedNews(ReservedNews reservedNews) {
		// TODO Auto-generated method stub
		this.entityManager.persist(reservedNews);
		return reservedNews;
	}

	@Override
	public void deleteReservedNews(ReservedNews reservedNews) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(reservedNews)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			reservedNews.setEnabled(!reservedNews.isEnabled());
			updateReservedNews(reservedNews);
		} else {
			ReservedNews deletereservedNews = findReservedNewsById(reservedNews.getId());
			deletereservedNews.setEnabled(!deletereservedNews.isEnabled());
			updateReservedNews(deletereservedNews);
		}
	}

	@Override
	public ReservedNews updateReservedNews(ReservedNews reservedNews) {
		// TODO Auto-generated method stub
		ReservedNews updateReservedNews = entityManager.merge(reservedNews);
		this.entityManager.flush();

		return updateReservedNews;
	}

	@Override
	public ReservedNews findReservedNewsById(Long id) {
		// TODO Auto-generated method stub
		if (id == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<ReservedNews> typedQuery = this.entityManager.createNamedQuery("ReservedNews.findReservedNewsById",
				ReservedNews.class);
		try {
			return typedQuery.setParameter("id", id).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public ReservedNews findReservedNewsByPostIdAndUsername(Long postid, String username) {
		// TODO Auto-generated method stub
		if ((postid == null) || (username == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<ReservedNews> typedQuery = this.entityManager
				.createNamedQuery("ReservedNews.findReservedNewsByPostIdAndUsername", ReservedNews.class);
		try {
			return typedQuery.setParameter("postid", postid).setParameter("username", username).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<ReservedNews> findAllReservedNewsByUsername(String username) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<ReservedNews> typedQuery = this.entityManager
				.createNamedQuery("ReservedNews.findAllReservedNewsByUsername", ReservedNews.class);
		try {
			return typedQuery.setParameter("username", username).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<ReservedNews> findAllReservedNewsByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<ReservedNews> typedQuery = this.entityManager
				.createNamedQuery("ReservedNews.findAllReservedNewsByUsernameAndPage", ReservedNews.class);
		try {
			return typedQuery.setParameter("username", username).setFirstResult(pageNum).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
