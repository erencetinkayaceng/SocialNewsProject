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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cruz.model.Comment;
import com.cruz.model.Post;
import com.cruz.model.user.User;
import com.cruz.service.CommentService;
import com.cruz.service.PostService;
import com.cruz.service.UserService;
import com.cruz.utils.GetLocalDateTime;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/newComment", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String setNewComment(@RequestParam("postLink") String postLink,
			@RequestParam("commentContent") String commentContent) {
		Post post = postService.findPostByPostLink(postLink);
		User user = userService.findUserByUsername(getPrincipal());

		Comment comment = new Comment();
		comment.setAddDate(GetLocalDateTime.getCurrentTimeStamp());
		comment.setUser(user);
		comment.setPost(post);
		comment.setContent(commentContent);

		commentService.saveComment(comment);
		List<Comment> commentList = new ArrayList<Comment>();
		commentList.add(comment);
		post.setComments(commentList);

		postService.updatePost(post);

		return messageSource.getMessage("message.success.create.comment", null, LocaleContextHolder.getLocale());
	}

	@PreAuthorize("@mySecurityService.canAccessCommentRemove(#commentID)")
	@RequestMapping(value = "/removeComment", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getRemoveComment(@RequestParam("commentID") String commentID, ModelMap model) {

		Comment comment = commentService.findComment(Long.parseLong(commentID));
		commentService.deleteComment(comment);

		return messageSource.getMessage("message.success.remove.comment", null, LocaleContextHolder.getLocale());
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
