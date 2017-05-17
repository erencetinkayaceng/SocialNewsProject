package com.cruz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cruz.model.user.User;

@Entity
@Table(name = "LIKES")
@NamedQueries({
		@NamedQuery(name = "Like.findLikeByPostID", query = "Select l From Like l JOIN l.post p Where p.id = :postID"),
		@NamedQuery(name = "Like.findLikeByPostLinkAndUsername", query = "Select l From Like l JOIN l.user u JOIN l.post p Where p.postLink = :postLink AND u.username = :username"),
		@NamedQuery(name = "Like.countLikesByPostID", query = "Select u From Like l JOIN l.user u JOIN l.post p Where p.id = :postID"),
		@NamedQuery(name = "Like.likeAllUserByPostID", query = "Select u From Like l JOIN l.user u JOIN l.post p Where p.id = :postID"), })
public class Like implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIKE_ID")
	private Long id;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> user = new ArrayList<User>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "POST_ID")
	private Post post;

	@Column
	private boolean enabled = true;

	public Like() {
		super();
	}

	public Like(Long id, List<User> user, Post post, boolean enabled) {
		super();
		this.id = id;
		this.user = user;
		this.post = post;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Like other = (Like) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
