package com.cruz.dto;

import java.util.Collection;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.cruz.model.user.Profile;
import com.cruz.model.user.Role;

public class UserDTO {

	private Long userid;
	
	@NotEmpty
	@Size(min = 5, max = 50)
	private String username;
	
	@NotEmpty
	@Size(min = 8)
	private String password;
	
	private String emailAddress;
	
	private String firstname;
	
	private String lastname;
	
    private boolean enabled;
    
    private Profile userProfile;
    
    private String userKey;
	 
	private Collection<Role> role;

	public UserDTO() {
		super();
	}

	public UserDTO(Long userid, String username, String password, String emailAddress, String firstname,
			String lastname, boolean enabled, Profile userProfile, String userKey, Collection<Role> role) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.userProfile = userProfile;
		this.userKey = userKey;
		this.role = role;
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

	public Profile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(Profile userProfile) {
		this.userProfile = userProfile;
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
		return "UserDTO [userid=" + userid + ", username=" + username + ", password=" + password + ", emailAddress="
				+ emailAddress + ", firstname=" + firstname + ", lastname=" + lastname + ", enabled=" + enabled
				+ ", userProfile=" + userProfile + ", userKey=" + userKey + ", role=" + role + "]";
	}
	
	
}
