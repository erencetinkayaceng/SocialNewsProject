package com.cruz.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cruz.dto.AjansDTO;
import com.cruz.dto.AuthorDTO;
import com.cruz.model.user.User;
import com.cruz.service.UserService;
import com.cruz.utils.ConverterDTO;
import com.cruz.utils.GetLocalDateTime;
import com.cruz.utils.HashMD5;

@Controller
@RequestMapping(value = "/admin")
public class AdminProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String IMAGE_PATH = "/resources/upload/profile";

	@Autowired
	UserService userService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/")
	public String index(ModelMap model) {
		return "/admin/index";
	}

	@PreAuthorize("hasRole('ROLE_AUTHOR') and authentication.name == #username")
	@RequestMapping(value = "/editAuthor/{username}", method = RequestMethod.GET)
	public String getProfileEditAuthorByUsername(@PathVariable("username") String username, ModelMap model) {

		User user = userService.findUserByUsername(username);
		AuthorDTO userdto = ConverterDTO.UserToAuthorDto(user);

		model.addAttribute("userdto", userdto);
		model.addAttribute("user", user);
		return "/admin/profile/editAuthorProfile";
	}

	@RequestMapping(value = "/editAuthorProfile", method = RequestMethod.POST)
	public String setProfileEditAuthorByUsername(@ModelAttribute("userdto") @Valid AuthorDTO userdto,
			BindingResult result, ModelMap model,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

		if (result.hasErrors()) {
			System.out.println("There are errors editAuthor" + result.getFieldError().toString());
			return "redirect:/admin/editAuthor/" + getPrincipal();
		}

		User user = ConverterDTO.updateAuthorDtoToUser(userdto, userService.findUserByUsername(getPrincipal()));
		try {
			user = checkAndSaveProfileImage(profileImage, user);
		} catch (Exception e) {
			result.reject(e.getMessage());
			model.addAttribute("user", userdto);
			return "redirect:/admin/editAuthor/" + getPrincipal();
		}
		userService.updateUser(user);

		model.addAttribute("user", user);
		model.addAttribute("operationmessage",
				messageSource.getMessage("message.editauthor.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/profile/" + getPrincipal();
	}

	@PreAuthorize("hasRole('ROLE_AJANS') and authentication.name == #username")
	@RequestMapping(value = "/editAjans/{username}", method = RequestMethod.GET)
	public String getProfileEditAjansByUsername(@PathVariable("username") String username, ModelMap model) {

		User user = userService.findUserByUsername(username);
		AjansDTO userdto = ConverterDTO.UserToAjansDto(user);

		model.addAttribute("userdto", userdto);
		model.addAttribute("user", user);
		return "/admin/profile/editAjansProfile";
	}

	@RequestMapping(value = "/editAjansProfile", method = RequestMethod.POST)
	public String setProfileEditAjansByUsername(@ModelAttribute("userdto") @Valid AjansDTO userdto,
			BindingResult result, ModelMap model,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

		if (result.hasErrors()) {
			System.out.println("There are errors editAjans");
			return "redirect:/admin/editAjans/" + getPrincipal();
		}

		User user = ConverterDTO.updateAjansDtoToUser(userdto, userService.findUserByUsername(getPrincipal()));
		try {
			user = checkAndSaveProfileImage(profileImage, user);
		} catch (Exception e) {
			result.reject(e.getMessage());
			model.addAttribute("user", userdto);
			return "redirect:/admin/editAuthor/" + getPrincipal();
		}
		userService.updateUser(user);

		model.addAttribute("user", user);
		model.addAttribute("operationmessage",
				messageSource.getMessage("message.editauthor.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/profile/" + getPrincipal();
	}

	@ModelAttribute("profile")
	public User authenticateUser() {
		User user = userService.findUserByUsername(getPrincipal());
		return user;
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	private User checkAndSaveProfileImage(MultipartFile profileImage, User user) throws RuntimeException, IOException {
		if (!profileImage.isEmpty()) {
			validateImage(profileImage);
			String specialFileName = getSpecialFileName(user.getUsername(), profileImage.getOriginalFilename());
			saveImageToServer(user.getUsername(), profileImage, specialFileName);
			user = saveImageToUser(user, specialFileName);
		}
		return user;
	}

	private void validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/jpeg") && !image.getContentType().equals("image/png")) {
			throw new RuntimeException("Only JPG OR PNG images are accepted");
		}
	}

	// resim isimleri username-zamanın hash değerinin ilk 8 hanesi ve
	// orjinal dosyanın uzantısı
	private String getSpecialFileName(String username, String filename) {
		System.err.println(GetLocalDateTime.getCurrentTimeStamp().toString());
		return username + "-" + HashMD5.hashmd5(GetLocalDateTime.getCurrentTimeStamp().toString().substring(0, 8))
				+ filename.substring(filename.lastIndexOf("."), filename.length());
	}

	private String getServletContextRealPath() {
		return servletContext.getRealPath(IMAGE_PATH);
	}

	private void saveImageToServer(String username, MultipartFile image, String filename)
			throws RuntimeException, IOException {
		try {
			File file = new File(getServletContextRealPath() + "/" + filename);
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			logger.info("file save to succes, path: " + file.getAbsolutePath());
		} catch (IOException e) {
			throw e;
		}
	}

	private User saveImageToUser(User user, String filename) {
		user.getUserProfile().getProfileImageSrc().setSrc(IMAGE_PATH + "/" + filename);
		return user;
	}

}
