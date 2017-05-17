package com.cruz.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cruz.dao.PostRepository;
import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.Post;
import com.cruz.model.enums.PostType;

@Repository
@Transactional
public class PostRepositoryImpl implements PostRepository {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	MessageSource messageSource;

	@Override
	public Post savePost(Post post) {
		// TODO Auto-generated method stub
		this.entityManager.persist(post);
		return post;
	}

	@Override
	public void deletePost(Post post) {
		// TODO Auto-generated method stub
		if (this.entityManager.contains(post)) {
			// silmiyoruz sadece görünürlüğünü kapatıyoruz yada açıyoruz
			post.setEnabled(!post.isEnabled());
			updatePost(post);
		} else {
			Post deletePost = findPostByPostID(post.getId());
			deletePost.setEnabled(!deletePost.isEnabled());
			updatePost(deletePost);
		}
	}

	@Override
	public Post updatePost(Post post) {
		// TODO Auto-generated method stub
		Post updatePost = entityManager.merge(post);
		this.entityManager.flush();

		return updatePost;
	}

	@Override
	public Post findPostByPostID(Long postID) {
		// TODO Auto-generated method stub
		if (postID == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("Post.findPostByPostID", Post.class);
		try {
			return typedQuery.setParameter("postID", postID).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public Post findPostByUserID(Long userID) {
		// TODO Auto-generated method stub
		if (userID == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("Post.findPostByUserID", Post.class);
		try {
			return typedQuery.setParameter("userID", userID).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> findAllPostByUsernameAndPostType(String username, String postType) {
		// TODO Auto-generated method stub
		if ((username == null) || (postType == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("Post.findAllPostByUsernameAndPostType",
				Post.class);
		try {
			return typedQuery.setParameter("username", username).setParameter("postType", postType).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<String> AllPostType() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add(PostType.NORMAL.getPostType());
		list.add(PostType.HEADLINE.getPostType());
		list.add(PostType.DRAFT.getPostType());
		return list;
	}

	@Override
	public Post findPostByPostLink(String postLink) {
		// TODO Auto-generated method stub
		if (postLink == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("Post.findPostByPostLink", Post.class);
		try {
			return typedQuery.setParameter("postLink", postLink).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> findAllPostByUsernameAndPage(String username, int pageNum, int pageSize, String postType,
			String updateOrderBy) {
		// TODO Auto-generated method stub

		if ((username == null) || (postType == null) || (updateOrderBy == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		TypedQuery<Post> typedQuery = null;
		if (updateOrderBy.equals("ASC")) {
			typedQuery = this.entityManager.createNamedQuery("Post.findAllPostByUsernameAndPageWithASC", Post.class);
		} else if (updateOrderBy.equals("DESC")) {
			typedQuery = this.entityManager.createNamedQuery("Post.findAllPostByUsernameAndPageWithDESC", Post.class);
		} else {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		try {
			return typedQuery.setParameter("username", username).setParameter("postType", postType)
					.setFirstResult(pageNum).setMaxResults(pageSize).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> doSearchFindAllByTitleAndContent(String searchWords) {
		// TODO Auto-generated method stub
		if ((searchWords == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		String[] searchWordsList = searchWords.split(" ");
		StringBuilder jpaql = new StringBuilder("Select p From Post p Where (");
		for (int i = 0; i < searchWordsList.length; i++) {
			jpaql.append("p.title LIKE '%" + searchWordsList[i] + "%' OR ");
			jpaql.append("p.content LIKE '%" + searchWordsList[i] + "%' ");
			if ((searchWordsList.length - 1) != i) {
				jpaql.append("OR ");
			}
		}
		jpaql.append(" ) AND not p.postType = 'Taslak' AND p.enabled = true ORDER BY p.updateDate DESC ");

		TypedQuery<Post> typedQuery = this.entityManager.createQuery(jpaql.toString(), Post.class);
		try {
			return typedQuery.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> doSearchFindAllByTitleAndContentAndPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if ((searchWords == null)) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		String[] searchWordsList = searchWords.split(" ");
		StringBuilder jpaql = new StringBuilder("Select p From Post p Where (");
		for (int i = 0; i < searchWordsList.length; i++) {
			jpaql.append("p.title LIKE '%" + searchWordsList[i] + "%' OR ");
			jpaql.append("p.content LIKE '%" + searchWordsList[i] + "%' ");
			if ((searchWordsList.length - 1) != i) {
				jpaql.append("OR ");
			}
		}
		jpaql.append(" ) AND not p.postType = 'Taslak' AND p.enabled = true ORDER BY p.updateDate DESC ");

		TypedQuery<Post> typedQuery = this.entityManager.createQuery(jpaql.toString(), Post.class);
		try {
			return typedQuery.setFirstResult(pageNum).setMaxResults(pageSize).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> doSearchFindAllByHashTag(String searchWords) {
		// TODO Auto-generated method stub
		if (searchWords == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("Post.doSearchFindAllByHashTag", Post.class);
		try {
			return typedQuery.setParameter("hashTag", searchWords).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Post> doSearchFindAllByHashTagAndPage(String searchWords, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		if (searchWords == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		TypedQuery<Post> typedQuery = this.entityManager.createNamedQuery("Post.doSearchFindAllByHashTag", Post.class);
		try {
			return typedQuery.setParameter("hashTag", searchWords).setFirstResult(pageNum).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> doSearchFindAllPopulerByHashTag() {
		// TODO Auto-generated method stub
		Query typedQuery = this.entityManager.createNamedQuery("Post.doSearchFindAllPopulerByHashTag");
		try {
			return typedQuery.setMaxResults(5).getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
