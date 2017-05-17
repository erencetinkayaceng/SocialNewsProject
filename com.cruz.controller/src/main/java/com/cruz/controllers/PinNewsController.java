package com.cruz.controllers;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cruz.model.Follow;
import com.cruz.model.PageHolder;
import com.cruz.model.PinNews;
import com.cruz.model.Post;
import com.cruz.model.user.User;
import com.cruz.service.FollowService;
import com.cruz.service.PinNewsService;
import com.cruz.service.PostService;
import com.cruz.service.UserService;
import com.cruz.utils.GetLocalDateTime;

@Controller
@RequestMapping(value = "/pinnews")
public class PinNewsController {

	private static final Logger logger = LoggerFactory.getLogger(PinNewsController.class);
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	PinNewsService pinNewsService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	FollowService followService;

	@RequestMapping(value = "/detail/{username}", method = RequestMethod.GET)
	public String getAllPinNews(ModelMap model, @PathVariable("username") String username,
			@ModelAttribute("operationmessage") String operationmessage,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		if (totalCount == 0) {
			totalCount = pinNewsService.findAllPinNewsByUsername(username).size();
		}
		List<PinNews> pinNewsList = pinNewsService.findAllPinNewsByUsernameAndPage(username, pageNum * pageSize,
				pageSize);
		List<Post> listPageHolder = new ArrayList<Post>();
		for (PinNews pinNews : pinNewsList) {
			listPageHolder.add(pinNews.getPost());
		}
		PageHolder<Post> pageHolder = new PageHolder<Post>(pageNum, pageSize, totalCount, listPageHolder,
				"pinnews/detail/" + username);
		User user = userService.findUserByUsername(username);

		model.addAttribute("pageHolder", pageHolder);
		model.addAttribute("user", user);
		model.addAttribute("followed", checkFollow(username));
		model.addAttribute("operationmessage", operationmessage);
		return "/user/pinnews/viewPinNews";
	}

	@PreAuthorize("@mySecurityService.canAccessPinNewsSaveByPostLink(#postlink)")
	@RequestMapping(value = "/savePinNews/{postlink}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getSavePinNews(@PathVariable("postlink") String postlink, ModelMap model) {
		Post post = postService.findPostByPostLink(postlink);
		PinNews rn = pinNewsService.findPinNewsByPostIdAndUsername(post.getId(), getPrincipal());
		if (rn == null) {
			PinNews pinNews = new PinNews();
			pinNews.setAddDate(GetLocalDateTime.getCurrentTimeStamp());
			pinNewsService.savePinNews(pinNews);

			pinNews.setPost(post);
			pinNews.setUser(userService.findUserByUsername(getPrincipal()));
			pinNewsService.updatePinNews(pinNews);
		} else if (!rn.isEnabled()) {
			rn.setEnabled(!rn.isEnabled());
			pinNewsService.updatePinNews(rn);
		}

		return messageSource.getMessage("message.success.create.pinnew", null, LocaleContextHolder.getLocale());
	}

	@PreAuthorize("@mySecurityService.canAccessPinNewsRemoveByPostLink(#postlink)")
	@RequestMapping(value = "/removePinNews/{postlink}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getRemovePinNews(@PathVariable("postlink") String postlink, ModelMap model) {

		Post post = postService.findPostByPostLink(postlink);
		PinNews pinNews = pinNewsService.findPinNewsByPostIdAndUsername(post.getId(), getPrincipal());
		pinNewsService.deletePinNews(pinNews);

		return messageSource.getMessage("message.success.remove.pinnew", null, LocaleContextHolder.getLocale());
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
