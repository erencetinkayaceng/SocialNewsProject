package com.cruz.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cruz.model.user.User;
import com.cruz.service.UserService;

@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/fileUploadPage", method = RequestMethod.GET)
	public String fileUploadPage(ModelMap model) {

		return "/admin/upload/upload";
	}

	@RequestMapping(value = "/fileUploadPage", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + "deneme");
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.err.println("Server File Location=" + serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + "deneme";
			} catch (Exception e) {
				return "You failed to upload " + "deneme" + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + "deneme" + " because the file was empty.";
		}
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

}
