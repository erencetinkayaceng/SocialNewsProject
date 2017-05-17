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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cruz.model.user.User;

@Entity
@Table(name = "COMMENT")
@NamedQueries({
		@NamedQuery(name = "Comment.findAllCommentByPostLink", query = "Select c From Comment c JOIN c.post p Where p.postLink = :postLink AND c.enabled = true ORDER BY c.addDate ASC"),
		@NamedQuery(name = "Comment.findAllCommentByPostLinkAndPage", query = "Select c From Comment c JOIN c.post p Where p.postLink = :postLink AND c.enabled = true ORDER BY c.addDate ASC"), })
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMENT_ID")
	private Long id;

	@Lob
	@Column
	private String content;

	@Column(name = "ADD_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date addDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne
	private Post post;

	@Column
	private boolean enabled = true;

	public Comment() {
		super();
	}

	public Comment(Long id, String content, Date addDate, User user, Post post, boolean enabled) {
		super();
		this.id = id;
		this.content = content;
		this.addDate = addDate;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
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
		Comment other = (Comment) obj;
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
