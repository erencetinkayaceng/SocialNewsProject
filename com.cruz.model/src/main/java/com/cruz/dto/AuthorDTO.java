package com.cruz.dto;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.cruz.model.Image;
import com.cruz.model.user.Role;
import com.cruz.validation.ValidEmail;

public class AuthorDTO {

	private Long userid;

	@NotEmpty
	@Size(min = 5, max = 50)
	private String username;

	@NotEmpty
	@Size(min = 8)
	private String password;

	@NotEmpty
	@ValidEmail
	private String emailAddress;

	@NotEmpty
	private String firstname;

	@NotEmpty
	private String lastname;

	private boolean enabled;

	private String userKey;

	private Collection<Role> role;

	private Long profileid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addDate;

	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date birthDate;

	@Lob
	private String description;

	private Image profileImageSrc;

	public AuthorDTO() {
		super();
	}

	public AuthorDTO(Long userid, String username, String password, String emailAddress, String firstname,
			String lastname, boolean enabled, String userKey, Collection<Role> role, Long profileid, Date addDate,
			Date birthDate, String description, Image profileImageSrc) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.userKey = userKey;
		this.role = role;
		this.profileid = profileid;
		this.addDate = addDate;
		this.birthDate = birthDate;
		this.description = description;
		this.profileImageSrc = profileImageSrc;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getProfileid() {
		return profileid;
	}

	public void setProfileid(Long profileid) {
		this.profileid = profileid;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getProfileImageSrc() {
		return profileImageSrc;
	}

	public void setProfileImageSrc(Image profileImageSrc) {
		this.profileImageSrc = profileImageSrc;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Collection<Role> getRole() {
		return role;
	}

	public void setRole(Collection<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AjansDTO [userid=" + userid + ", username=" + username + ", emailAddress=" + emailAddress
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", enabled=" + enabled + ", userKey="
				+ userKey + ", role=" + role + ", profileid=" + profileid + ", addDate=" + addDate + ", birthDate="
				+ birthDate + ", description=" + description + ", profileImageSrc=" + profileImageSrc + "]";
	}

}
