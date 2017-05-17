package com.cruz.utils;

import com.cruz.dto.AjansDTO;
import com.cruz.dto.AuthorDTO;
import com.cruz.model.Image;
import com.cruz.model.enums.RoleEnum;
import com.cruz.model.user.AjansProfile;
import com.cruz.model.user.AuthorProfile;
import com.cruz.model.user.Profile;
import com.cruz.model.user.Role;
import com.cruz.model.user.User;


public class ConverterDTO {

	public static User createUserByAuthorDTO(AuthorDTO userdto) {
		// TODO Auto-generated method stub
		User user = new User();
		Profile profile = new AuthorProfile();
		Role role = new Role();

		user.setUsername(userdto.getUsername());
		user.setPassword(userdto.getPassword());
		user.setEmailAddress(userdto.getEmailAddress());
		user.setFirstname(userdto.getFirstname());
		user.setLastname(userdto.getLastname());
		user.setUserKey("1");
		
		role.setRole(RoleEnum.AUTHOR.getUserRoleType());
		role.setUser(user);
		role.setEnabled(true);

		Image image = new Image();
		image.setAddDate(GetLocalDateTime.getCurrentTimeStamp());
		image.setUsername(user.getUsername());
		image.setSrc("/resources/images/info.png");

		profile.setAddDate(GetLocalDateTime.getCurrentTimeStamp());
		profile.setBirthDate(userdto.getBirthDate());
		profile.setDescription(userdto.getDescription());
		profile.setProfileImageSrc(image);
		profile.setUser(user);

		user.getRole().add(role);
		user.setUserProfile(profile);
		return user;
	}

	public static User createUserByAjansDTO(AjansDTO userdto) {
		// TODO Auto-generated method stub
		User user = new User();
		Profile profile = new AjansProfile();
		Role role = new Role();
		
		user.setUsername(userdto.getUsername());
		user.setPassword(userdto.getPassword());
		user.setEmailAddress(userdto.getEmailAddress());
		user.setFirstname(userdto.getFirstname());
		user.setLastname(userdto.getLastname());
		user.setUserKey("1");
		
		role.setRole(RoleEnum.AJANS.getUserRoleType());
		role.setUser(user);
		role.setEnabled(true);

		Image image = new Image();
		image.setAddDate(GetLocalDateTime.getCurrentTimeStamp());
		image.setUsername(user.getUsername());
		image.setSrc("/resources/images/info.png");

		profile.setAddDate(GetLocalDateTime.getCurrentTimeStamp());
		profile.setBirthDate(userdto.getBirthDate());
		profile.setDescription(userdto.getDescription());
		profile.setProfileImageSrc(image);
		profile.setUser(user);

		
		user.getRole().add(role);
		user.setUserProfile(profile);
		return user;
	}

	public static AuthorDTO UserToAuthorDto(User user){
		AuthorDTO userdto=new AuthorDTO();
		
		userdto.setUserid(user.getId());
		userdto.setUsername(user.getUsername());
		userdto.setPassword(user.getPassword());
		userdto.setEmailAddress(user.getEmailAddress());
		userdto.setFirstname(user.getFirstname());
		userdto.setLastname(user.getLastname());
		userdto.setUserKey(user.getUserKey());		
		userdto.setRole(user.getRole());
		userdto.setEnabled(user.isEnabled());
		
		userdto.setProfileid(user.getUserProfile().getId());
		userdto.setAddDate(user.getUserProfile().getAddDate());
		userdto.setBirthDate(user.getUserProfile().getBirthDate());
		userdto.setDescription(user.getUserProfile().getDescription());	
		
		return userdto;
	}
	
	public static AjansDTO UserToAjansDto(User user){
		AjansDTO userdto=new AjansDTO();
		
		userdto.setUserid(user.getId());
		userdto.setUsername(user.getUsername());
		userdto.setPassword(user.getPassword());
		userdto.setEmailAddress(user.getEmailAddress());
		userdto.setFirstname(user.getFirstname());
		userdto.setLastname(user.getLastname());
		userdto.setUserKey(user.getUserKey());		
		userdto.setRole(user.getRole());
		userdto.setEnabled(user.isEnabled());
		
		userdto.setProfileid(user.getUserProfile().getId());
		userdto.setAddDate(user.getUserProfile().getAddDate());
		userdto.setBirthDate(user.getUserProfile().getBirthDate());
		userdto.setDescription(user.getUserProfile().getDescription());		
		userdto.setProfileImageSrc(user.getUserProfile().getProfileImageSrc());
		
		return userdto;
	}

	public static User updateAuthorDtoToUser(AuthorDTO userdto , User user){
				
		user.setEmailAddress(userdto.getEmailAddress());
		user.setFirstname(userdto.getFirstname());
		user.setLastname(userdto.getLastname());	
		
		user.getUserProfile().setAddDate(userdto.getAddDate());
		user.getUserProfile().setBirthDate(userdto.getBirthDate());
		user.getUserProfile().setDescription(userdto.getDescription());
				
		return user;
	}
	
	public static User updateAjansDtoToUser(AjansDTO userdto , User user){
		
		user.setEmailAddress(userdto.getEmailAddress());
		user.setFirstname(userdto.getFirstname());
		user.setLastname(userdto.getLastname());	
		
		user.getUserProfile().setAddDate(userdto.getAddDate());
		user.getUserProfile().setBirthDate(userdto.getBirthDate());
		user.getUserProfile().setDescription(userdto.getDescription());
		
		return user;
	}


}
