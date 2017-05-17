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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String IMAGE_PATH = "/resources/upload/profile";

	@Autowired
	UserService userService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/user/newAuthor", method = RequestMethod.GET)
	public String getNewAuthorRegistration(ModelMap model) {
		AuthorDTO user = new AuthorDTO();
		model.addAttribute("user", user);
		return "/user/newAuthor";
	}

	/*
	 * This method will be called on form submission, handling POST request It
	 * also validates the user input
	 */
	@RequestMapping(value = "/user/newAuthor", method = RequestMethod.POST)
	public String setNewAuthorRegistration(@ModelAttribute("user") @Valid AuthorDTO userdto, BindingResult result,
			ModelMap model, @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
		if (result.hasErrors()) {
			System.out.println("There are errors newAjans");
			model.addAttribute("user", userdto);
			return "/user/newAuthor";
		}

		User user = ConverterDTO.createUserByAuthorDTO(userdto);
		try {
			user = checkAndSaveProfileImage(profileImage, user);
		} catch (Exception e) {
			result.reject(e.getMessage());
			model.addAttribute("user", userdto);
			return "/user/newAuthor";
		}
		userService.saveUser(user);

		model.addAttribute("operationmessage",
				messageSource.getMessage("message.newauthor.success", null, LocaleContextHolder.getLocale()));
		return "/login/login";
	}

	@RequestMapping(value = "/user/newAjans", method = RequestMethod.GET)
	public String getNewAjansRegistration(ModelMap model) {
		AjansDTO user = new AjansDTO();
		model.addAttribute("user", user);
		return "/user/newAjans";
	}

	/*
	 * This method will be called on form submission, handling POST request It
	 * also validates the user input
	 */
	@RequestMapping(value = "/user/newAjans", method = RequestMethod.POST)
	public String setNewAjansRegistration(@ModelAttribute("user") @Valid AjansDTO userdto, BindingResult result,
			ModelMap model, @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

		if (result.hasErrors()) {
			System.out.println("There are errors newAjans");
			model.addAttribute("user", userdto);
			return "/user/newAjans";
		}

		User user = ConverterDTO.createUserByAjansDTO(userdto);
		try {
			user = checkAndSaveProfileImage(profileImage, user);
		} catch (Exception e) {
			result.reject(e.getMessage());
			model.addAttribute("user", userdto);
			return "/user/newAuthor";
		}
		userService.saveUser(user);

		model.addAttribute("operationmessage",
				messageSource.getMessage("message.newajans.success", null, LocaleContextHolder.getLocale()));
		return "/login/login";
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
