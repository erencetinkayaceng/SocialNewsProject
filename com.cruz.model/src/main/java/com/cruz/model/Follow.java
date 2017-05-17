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
@Table(name = "FOLLOW")
@NamedQueries({
		@NamedQuery(name = "Follow.findFollowerByUsername", query = "Select f From Follow f  JOIN f.follower fer JOIN f.followed fed Where fer.username = :fer and fed.username = :fed"),
		@NamedQuery(name = "Follow.findAllFollowerByUsername", query = "Select f From Follow f JOIN f.followed u Where u.username = :followed and f.enabled=true"),
		@NamedQuery(name = "Follow.findAllFollowerByUsernameAndPage", query = "Select f From Follow f JOIN f.followed u Where u.username = :followed and f.enabled=true ORDER BY f.followdate"),
		@NamedQuery(name = "Follow.findAllFollowedByUsername", query = "Select f From Follow f JOIN f.follower u Where u.username = :follower and f.enabled=true"),
		@NamedQuery(name = "Follow.findAllFollowedByUsernameAndPage", query = "Select f From Follow f JOIN f.follower u Where u.username = :follower and f.enabled=true ORDER BY f.followdate"), })
public class Follow implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOLLOW_ID")
	private Long id;

	// takip eden
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FOLLOWER_ID")
	private User follower;

	// takip edilen
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FOLLOWED_ID")
	private User followed;

	@Column(name = "FOLLOW_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date followdate;

	@Column
	private boolean enabled = true;

	public Follow() {
		super();
	}

	public Follow(Long id, User follower, User followed, Date followdate, boolean enabled) {
		super();
		this.id = id;
		this.follower = follower;
		this.followed = followed;
		this.followdate = followdate;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getFollowed() {
		return followed;
	}

	public void setFollowed(User followed) {
		this.followed = followed;
	}

	public Date getFollowdate() {
		return followdate;
	}

	public void setFollowdate(Date followdate) {
		this.followdate = followdate;
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
		Follow other = (Follow) obj;
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
