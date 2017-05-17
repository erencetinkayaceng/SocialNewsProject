package com.cruz.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cruz.model.Follow;
import com.cruz.model.PageHolder;
import com.cruz.model.Post;
import com.cruz.model.enums.PostType;
import com.cruz.model.user.User;
import com.cruz.service.FollowService;
import com.cruz.service.PostService;
import com.cruz.service.UserService;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	FollowService followService;

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public String getProfileViewByUsername(@ModelAttribute("operationmessage") String operationmessage, ModelMap model,
			@PathVariable("username") String username, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		if (totalCount == 0) {
			totalCount = postService.findAllPostByUsernameAndPostType(username, PostType.NORMAL.getPostType()).size();
		}
		List<Post> listByPageHolder = postService.findAllPostByUsernameAndPage(username, pageNum * pageSize, pageSize,
				PostType.NORMAL.getPostType(), "DESC");
		PageHolder<Post> pageHolder = new PageHolder<Post>(pageNum, pageSize, totalCount, listByPageHolder,
				"profile/" + username);
		User user = userService.findUserByUsername(username);

		model.addAttribute("followed", checkFollow(username));
		model.addAttribute("operationmessage", operationmessage);
		model.addAttribute("pageHolder", pageHolder);
		model.addAttribute("user", user);
		model.addAttribute("headLinePosts",
				postService.findAllPostByUsernameAndPage(username, 0, 15, PostType.HEADLINE.getPostType(), "DESC"));
		return "/user/profile/viewProfile";
	}

	@RequestMapping(value = "/detail/{username}", method = RequestMethod.GET)
	public String getProfileDetailByUsername(@PathVariable("username") String username, ModelMap model) {
		User user = userService.findUserByUsername(username);

		model.addAttribute("followed", checkFollow(username));
		model.addAttribute("user", user);
		return "/user/profile/detailProfile";
	}

	@ModelAttribute("profile")
	public User authenticateUser() {
		User user = userService.findUserByUsername(getPrincipal());
		return user;
	}

	@ModelAttribute("popularHashtags")
	public List<String> populerHashtags() {
		List<String> list = postService.doSearchFindAllPopulerByHashTag();
		return list;
	}

	private boolean checkFollow(String followed) {
		Follow follow = followService.findFollow(getPrincipal(), followed);
		if (follow == null) {
			return false;
		}
		return follow.isEnabled();
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
