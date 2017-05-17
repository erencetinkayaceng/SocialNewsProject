package com.cruz.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cruz.model.user.User;

@Entity
@Table(name = "PINNEWS")
@NamedQueries({ @NamedQuery(name = "PinNews.findPinNewsById", query = "Select pn From PinNews pn Where pn.id = :id"),
		@NamedQuery(name = "PinNews.findPinNewsByPostIdAndUsername", query = "Select pn From PinNews pn JOIN pn.user u JOIN pn.post p Where u.username = :username AND p.id=:postid"),
		@NamedQuery(name = "PinNews.findAllPinNewsByUsername", query = "Select pn From PinNews pn JOIN pn.user u JOIN pn.post p Where u.username = :username AND pn.enabled = true AND p.enabled = true ORDER BY pn.addDate DESC"),
		@NamedQuery(name = "PinNews.findAllPinNewsByUsernameAndPage", query = "Select pn From PinNews pn JOIN pn.user u JOIN pn.post p Where u.username = :username AND pn.enabled = true AND p.enabled = true ORDER BY pn.addDate DESC"), })
public class PinNews implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PINNEWS_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POST_ID")
	private Post post;

	@Column(name = "ADD_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date addDate;

	@Column
	private boolean enabled = true;

	public PinNews() {
		super();
	}

	public PinNews(Long id, User user, Post post, Date addDate, boolean enabled) {
		super();
		this.id = id;
		this.user = user;
		this.post = post;
		this.addDate = addDate;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
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
		PinNews other = (PinNews) obj;
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
