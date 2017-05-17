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

import com.cruz.model.Comment;
import com.cruz.model.PageHolder;
import com.cruz.model.Post;
import com.cruz.model.user.User;
import com.cruz.service.CommentService;
import com.cruz.service.PostService;
import com.cruz.service.UserService;

@Controller
@RequestMapping(value = "/post")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	CommentService commentService;

	@RequestMapping(value = "/detail/{postLink}", method = RequestMethod.GET)
	public String getDetailPost(@PathVariable("postLink") String postLink, ModelMap model,
			@RequestParam(value = "commentNum", defaultValue = "0") int commentNum,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		if (totalCount == 0) {
			totalCount = commentService.findAllCommentByPostLink(postLink).size();
		}
		List<Comment> commentList = commentService.findAllCommentByPostLinkAndPage(postLink, commentNum, pageSize);
		PageHolder<Comment> pageHolder = new PageHolder<Comment>(commentNum, pageSize, totalCount, commentList,
				"post/detail/" + postLink);
		Post post = postService.findPostByPostLink(postLink);

		model.addAttribute("pageHolder", pageHolder);
		model.addAttribute("post", post);
		return "/post/detailPost";
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
