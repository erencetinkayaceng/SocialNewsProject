package com.cruz.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cruz.model.Like;
import com.cruz.model.PageHolder;
import com.cruz.model.Post;
import com.cruz.model.user.User;
import com.cruz.service.LikeService;
import com.cruz.service.PostService;
import com.cruz.service.UserService;
import com.cruz.utils.GetLocalDateTime;
import com.cruz.utils.HashMD5;

@Controller
@RequestMapping(value = "/admin")
public class AdminPostController {

	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	LikeService likeService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/viewPosts" }, method = RequestMethod.GET)
	public String getViewPosts(ModelMap model, @ModelAttribute("operationmessage") String operationmessage,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount,
			@RequestParam(value = "postType", defaultValue = "%") String postType,
			@RequestParam(value = "updateOrderBy", defaultValue = "DESC") String updateOrderBy) {

		if (totalCount == 0) {
			totalCount = postService.findAllPostByUsernameAndPostType(getPrincipal(), postType).size();
		}
		List<Post> listByPageHolder = postService.findAllPostByUsernameAndPage(getPrincipal(), pageNum * pageSize,
				pageSize, postType, updateOrderBy);
		PageHolder<Post> pageHolder = new PageHolder<Post>(pageNum, pageSize, totalCount, listByPageHolder,
				"admin/viewPosts");

		List<String> allPostTypes = postService.AllPostType();
		allPostTypes.add(0,
				messageSource.getMessage("label.post.type.option.all", null, LocaleContextHolder.getLocale()));
		model.addAttribute("allPostTypes", allPostTypes);
		model.addAttribute("pageHolder", pageHolder);
		model.addAttribute("reqPostType", postType);
		model.addAttribute("reqUpdateOrderBy", updateOrderBy);
		model.addAttribute("operationmessage", operationmessage);
		return "/admin/post/viewPosts";
	}

	@RequestMapping(value = "/newPost", method = RequestMethod.GET)
	public String getNewPost(ModelMap model) {
		Post post = new Post();
		model.addAttribute("post", post);
		model.addAttribute("allpostTypes", postService.AllPostType());
		return "/admin/post/newPost";
	}

	/*
	 * This method will be called on form submission, handling POST request It
	 * also validates the user input
	 */
	@RequestMapping(value = "/newPost", method = RequestMethod.POST)
	public String setNewPost(@ModelAttribute("post") @Valid Post post, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			System.out.println("There are errors newPost");
			model.addAttribute("allpostTypes", postService.AllPostType());
			return "/admin/post/newPost";
		}

		post = createPost(post);

		Like like = new Like();
		like.setPost(post);
		likeService.saveLike(like);

		User user = userService.findUserByUsername(getPrincipal());
		post.setUser(user);
		postService.updatePost(post);

		model.addAttribute("operationmessage",
				messageSource.getMessage("message.newpost.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/profile/" + getPrincipal();
	}

	@PreAuthorize("@mySecurityService.canAccessPostEditByPostLink(#postlink)")
	@RequestMapping(value = "/editPost/{postlink}", method = RequestMethod.GET)
	public String getEditPostByPostLink(@PathVariable("postlink") String postlink, ModelMap model) {
		Post post = postService.findPostByPostLink(postlink);
		model.addAttribute("allpostTypes", postService.AllPostType());
		model.addAttribute("post", post);
		return "/admin/post/editPost";
	}

	@RequestMapping(value = "/editPost", method = RequestMethod.POST)
	public String setEditPostByPostLink(@ModelAttribute("post") @Valid Post post, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			System.out.println("There are errors newPost");
			model.addAttribute("allpostTypes", postService.AllPostType());
			return "/admin/post/editPost";
		}

		Post _post = postService.updatePost(editablePost(post));

		model.addAttribute("post", _post);
		model.addAttribute("operationmessage",
				messageSource.getMessage("message.editpost.success", null, LocaleContextHolder.getLocale()));
		return "redirect:/profile/" + getPrincipal();
	}

	@PreAuthorize("@mySecurityService.canAccessPostEdit(#postlink)")
	@RequestMapping(value = "/removePost/{postlink}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getRemovePostByPostLink(@PathVariable("postlink") String postlink, ModelMap model) {
		Post post = postService.findPostByPostLink(postlink);
		postService.deletePost(post);

		return messageSource.getMessage("message.removepost.success", null, LocaleContextHolder.getLocale());
	}

	@ModelAttribute("profile")
	public User authenticateUser() {
		User user = userService.findUserByUsername(getPrincipal());
		return user;
	}

	private Post editablePost(Post post) {
		Post _post = postService.findPostByPostLink(post.getPostLink());

		Date date = GetLocalDateTime.getCurrentTimeStamp();
		_post.setTitle(post.getTitle());
		_post.setHashTag(post.getHashTag());
		_post.setContent(post.getContent());
		_post.setPostType(post.getPostType());
		_post.setTitle(post.getTitle());
		_post.setPostLink(post.getTitle().replace(" ", "_") + "_" + HashMD5.hashmd5(date.toString()).substring(0, 10));
		_post.setUpdateDate(GetLocalDateTime.getCurrentTimeStamp());

		return _post;
	}

	private Post createPost(Post post) {
		Date date = GetLocalDateTime.getCurrentTimeStamp();
		post.setAddDate(date);
		post.setUpdateDate(date);
		post.setPostLink(post.getTitle().replace(" ", "_") + "_" + HashMD5.hashmd5(date.toString()).substring(0, 10));
		return post;
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
