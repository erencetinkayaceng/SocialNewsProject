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

import com.cruz.dao.UserRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.user.User;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public User saveUser(User User) {
		// TODO Auto-generated method stub
		this.entityManager.persist(User);
		return User;
	}

	@Override
	public void deleteUser(User User) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(User)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			User.setEnabled(!User.isEnabled());
			updateUser(User);
		} else {
			User deleteUser = findUserByUserID(User.getId());
			deleteUser.setEnabled(!deleteUser.isEnabled());
			updateUser(deleteUser);
		}

	}

	@Override
	public User updateUser(User User) {
		// TODO Auto-generated method stub
		User updatedUser = entityManager.merge(User);
		this.entityManager.flush();

		return updatedUser;
	}

	@Override
	public User findUserByUserID(Long UserID) {
		// TODO Auto-generated method stub
		if (UserID == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		try {
			return this.entityManager.find(User.class, UserID);
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		if (username == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<User> typedQuery = this.entityManager.createNamedQuery("User.findUserByUsername", User.class);
		try {
			return typedQuery.setParameter("username", username).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<User> doSearchFindAllByUsernameAndFullName(String searchWords) {
		// TODO Auto-generated method stub
		if ((searchWords == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		String[] searchWordsList = searchWords.split(" ");
		StringBuilder jpaql = new StringBuilder("Select u From User u Where (");
		for (int i = 0; i < searchWordsList.length; i++) {
			jpaql.append("u.username LIKE '%" + searchWordsList[i] + "%' OR ");
			jpaql.append("u.firstname LIKE '%" + searchWordsList[i] + "%' OR ");
			jpaql.append("u.lastname LIKE '%" + searchWordsList[i] + "%' ");
			if ((searchWordsList.length - 1) != i) {
				jpaql.append("OR ");
			}
		}
		jpaql.append(")");

		TypedQuery<User> typedQuery = this.entityManager.createQuery(jpaql.toString(), User.class);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<User> doSearchFindAllByUsernameAndFullNameAndPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if ((searchWords == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		String[] searchWordsList = searchWords.split(" ");
		StringBuilder jpaql = new StringBuilder("Select u From User u Where (");
		for (int i = 0; i < searchWordsList.length; i++) {
			jpaql.append("u.username LIKE '%" + searchWordsList[i] + "%' OR ");
			jpaql.append("u.firstname LIKE '%" + searchWordsList[i] + "%' OR ");
			jpaql.append("u.lastname LIKE '%" + searchWordsList[i] + "%' ");
			if ((searchWordsList.length - 1) != i) {
				jpaql.append("OR ");
			}
		}
		jpaql.append(")");

		TypedQuery<User> typedQuery = this.entityManager.createQuery(jpaql.toString(), User.class);
		try {
			return typedQuery.setFirstResult(pageNum).setMaxResults(pageSize).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
