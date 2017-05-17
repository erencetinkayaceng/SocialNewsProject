package com.cruz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cruz.model.Like;
import com.cruz.model.Post;
import com.cruz.model.user.User;
import com.cruz.service.LikeService;
import com.cruz.service.PostService;
import com.cruz.service.UserService;

@Controller
@RequestMapping(value = "/like")
public class LikeController {

	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	LikeService likeService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/createLike/{postLink}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getCreateLike(@PathVariable("postLink") String postLink, ModelMap model) {
		Post post = postService.findPostByPostLink(postLink);
		User user = userService.findUserByUsername(getPrincipal());
		Like prevLike = likeService.findLikeByPostLinkAndUsername(postLink, getPrincipal());
		if (prevLike == null) {
			Like like = likeService.findLikeByPostID(post.getId());
			List<User> userList = likeService.likeAllUserByPostID(post.getId());
			userList.add(user);
			like.setUser(userList);
			likeService.updateLike(like);
		}

		post.setLikeCount(post.getLikeCount() + 1);
		postService.updatePost(post);

		return messageSource.getMessage("message.success.create.like", null, LocaleContextHolder.getLocale());
	}

	@RequestMapping(value = "/removeLike/{postLink}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getRemoveLike(@PathVariable("postLink") String postLink, ModelMap model) {
		Post post = postService.findPostByPostLink(postLink);
		Like like = likeService.findLikeByPostLinkAndUsername(postLink, getPrincipal());
		User user = userService.findUserByUsername(getPrincipal());

		if (like != null) {
			List<User> userList = likeService.likeAllUserByPostID(post.getId());
			userList.remove(user);
			like.setUser(userList);
			likeService.updateLike(like);
		}

		post.setLikeCount(post.getLikeCount() - 1);
		postService.updatePost(post);

		return messageSource.getMessage("message.success.remove.like", null, LocaleContextHolder.getLocale());
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
