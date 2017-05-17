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

import com.cruz.dao.PinNewsRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.PinNews;

@Repository
@Transactional
public class PinNewsRepositoryImpl implements PinNewsRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public PinNews savePinNews(PinNews pinNews) {
		// TODO Auto-generated method stub
		this.entityManager.persist(pinNews);
		return pinNews;
	}

	@Override
	public void deletePinNews(PinNews pinNews) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(pinNews)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			pinNews.setEnabled(!pinNews.isEnabled());
			updatePinNews(pinNews);
		} else {
			PinNews deletepinNews = findPinNewsById(pinNews.getId());
			deletepinNews.setEnabled(!deletepinNews.isEnabled());
			updatePinNews(deletepinNews);
		}
	}

	@Override
	public PinNews updatePinNews(PinNews pinNews) {
		// TODO Auto-generated method stub
		PinNews updatePinNews = entityManager.merge(pinNews);
		this.entityManager.flush();

		return updatePinNews;
	}

	@Override
	public PinNews findPinNewsById(Long id) {
		// TODO Auto-generated method stub
		if (id == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<PinNews> typedQuery = this.entityManager.createNamedQuery("PinNews.findPinNewsById", PinNews.class);
		try {
			return typedQuery.setParameter("id", id).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public PinNews findPinNewsByPostIdAndUsername(Long postid, String username) {
		// TODO Auto-generated method stub
		if ((postid == null) || (username == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<PinNews> typedQuery = this.entityManager.createNamedQuery("PinNews.findPinNewsByPostIdAndUsername",
				PinNews.class);
		try {
			return typedQuery.setParameter("postid", postid).setParameter("username", username).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<PinNews> findAllPinNewsByUsername(String username) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<PinNews> typedQuery = this.entityManager.createNamedQuery("PinNews.findAllPinNewsByUsername",
				PinNews.class);
		try {
			return typedQuery.setParameter("username", username).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<PinNews> findAllPinNewsByUsernameAndPage(String username, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<PinNews> typedQuery = this.entityManager.createNamedQuery("PinNews.findAllPinNewsByUsernameAndPage",
				PinNews.class);
		try {
			return typedQuery.setParameter("username", username).setFirstResult(pageNum).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
