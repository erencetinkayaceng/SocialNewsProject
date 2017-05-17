package com.cruz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.cruz.model.user.User;
import com.cruz.validation.ValidHashtag;

@Entity
@Table(name = "POST")
@NamedQueries({
		@NamedQuery(name = "Post.findPostByPostID", query = "Select p From Post p Where p.id = :postID AND p.enabled = true"),
		@NamedQuery(name = "Post.findPostByUserID", query = "Select p From Post p JOIN p.user u Where u.id = :userID AND p.enabled = true"),
		@NamedQuery(name = "Post.findPostByPostLink", query = "Select p From Post p Where p.postLink = :postLink AND p.enabled = true ORDER BY p.updateDate DESC"),
		@NamedQuery(name = "Post.findAllPostByUsernameAndPostType", query = "Select p From Post p JOIN p.user u Where u.username = :username AND p.postType LIKE :postType ORDER BY p.updateDate DESC"),
		@NamedQuery(name = "Post.findAllPostByUsernameAndPageWithASC", query = "Select p From Post p JOIN p.user u Where u.username = :username AND p.postType LIKE :postType ORDER BY p.updateDate ASC"),
		@NamedQuery(name = "Post.findAllPostByUsernameAndPageWithDESC", query = "Select p From Post p JOIN p.user u Where u.username = :username AND p.postType LIKE :postType ORDER BY p.updateDate DESC"),
		@NamedQuery(name = "Post.doSearchFindAllByHashTag", query = "Select p From Post p Where p.hashTag LIKE CONCAT('%',:hashTag,'%') AND not p.postType = 'Taslak' AND p.enabled = true ORDER BY p.updateDate DESC"),
		@NamedQuery(name = "Post.doSearchFindAllPopulerByHashTag", query = "Select p.hashTag From Post p Where p.enabled = true AND not p.postType = 'Taslak' GROUP BY p.hashTag ORDER BY COUNT(*) DESC"),
		@NamedQuery(name = "StrategyMainPost.fetchAllByStrategyFollow", query = "Select p From Post p Where p.user IN (Select f.followed From Follow f JOIN f.follower u Where u.username = :username and f.enabled=true) AND p.enabled = true AND not p.postType = 'Taslak' ORDER BY p.updateDate DESC"),
		@NamedQuery(name = "StrategyMainPost.fetchAllByStrategyPopularPost", query = "Select p From Post p Where p.enabled = true AND not p.postType = 'Taslak' ORDER BY p.likeCount DESC"), })
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_ID")
	private Long id;

	@NotEmpty
	@Column
	private String title;

	@Lob
	@Column
	private String content;

	@Size(min = 3, max = 120)
	@ValidHashtag
	private String hashTag;

	@Column(name = "ADD_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date addDate;

	@Column(name = "UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@NotEmpty
	@Size(max = 15)
	@Column
	private String postType;

	@Column(unique = true)
	private String postLink;

	@Column
	private boolean enabled = true;

	@Column
	private Long likeCount = (long) 0;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "IMAGE_ID")
	private Image headlineImageSrc;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Image> images = new ArrayList<Image>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Video> videos = new ArrayList<Video>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<Comment>();

	public Post() {
		super();
	}

	public Post(Long id, String title, String content, String hashTag, Date addDate, Date updateDate, String postType,
			String postLink, boolean enabled, User user, Long likeCount, Image headlineImageSrc) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.hashTag = hashTag;
		this.addDate = addDate;
		this.updateDate = updateDate;
		this.postType = postType;
		this.postLink = postLink;
		this.enabled = enabled;
		this.user = user;
		this.likeCount = likeCount;
		this.headlineImageSrc = headlineImageSrc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPostLink() {
		return postLink;
	}

	public void setPostLink(String postLink) {
		this.postLink = postLink;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Image getHeadlineImageSrc() {
		return headlineImageSrc;
	}

	public void setHeadlineImageSrc(Image headlineImageSrc) {
		this.headlineImageSrc = headlineImageSrc;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
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
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", hashTag=" + hashTag + ", addDate="
				+ addDate + ", updateDate=" + updateDate + ", postType=" + postType + ", postLink=" + postLink
				+ ", enabled=" + enabled + ", likeCount=" + likeCount + ", user=" + user + "]";
	}

}
