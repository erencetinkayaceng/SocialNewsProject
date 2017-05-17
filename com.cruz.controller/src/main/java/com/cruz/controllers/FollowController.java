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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cruz.model.Follow;
import com.cruz.model.PageHolder;
import com.cruz.model.user.User;
import com.cruz.service.FollowService;
import com.cruz.service.UserService;
import com.cruz.utils.GetLocalDateTime;

@Controller
@RequestMapping(value = "/follow")
public class FollowController {

	@Autowired
	UserService userService;
	@Autowired
	FollowService followService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/allFollower/{username}", method = RequestMethod.GET)
	public String getAllFollower(@PathVariable("username") String username, ModelMap model,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "9") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		if (totalCount == 0) {
			totalCount = followService.findAllFollowerByUsername(username).size();
		}
		List<Follow> followList = followService.findAllFollowerByUsernameAndPage(username, pageNum * pageSize,
				pageSize);
		PageHolder<Follow> pageHolder = new PageHolder<Follow>(pageNum, pageSize, totalCount, followList,
				"follow/allFollower/" + username);
		User user = userService.findUserByUsername(username);

		model.addAttribute("pageHolder", pageHolder);
		model.addAttribute("followed", checkFollow(username));
		model.addAttribute("user", user);
		return "/user/follow/follower";
	}

	@RequestMapping(value = "/allFollowed/{username}", method = RequestMethod.GET)
	public String getAllFollowed(@PathVariable("username") String username, ModelMap model,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "9") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		if (totalCount == 0) {
			totalCount = followService.findAllFollowedByUsername(username).size();
		}
		List<Follow> followList = followService.findAllFollowedByUsernameAndPage(username, pageNum * pageSize,
				pageSize);
		PageHolder<Follow> pageHolder = new PageHolder<Follow>(pageNum, pageSize, totalCount, followList,
				"follow/allFollowed/" + username);
		User user = userService.findUserByUsername(username);

		model.addAttribute("pageHolder", pageHolder);
		model.addAttribute("followed", checkFollow(username));
		model.addAttribute("user", user);
		return "/user/follow/followed";
	}

	@RequestMapping(value = "/newFollow/{username}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getNewFollow(@PathVariable("username") String username, ModelMap model) {

		Follow follow = followService.findFollow(getPrincipal(), username);

		if (follow == null) {
			Follow _follow = new Follow();
			_follow.setFollowdate(GetLocalDateTime.getCurrentTimeStamp());
			followService.saveFollow(_follow);

			_follow.setFollower(userService.findUserByUsername(getPrincipal()));
			_follow.setFollowed(userService.findUserByUsername(username));
			followService.updateFollow(_follow);
		} else {
			followService.deleteFollow(follow);
		}
		return messageSource.getMessage("message.success.create.follow", null, LocaleContextHolder.getLocale());
	}

	@RequestMapping(value = "/removeFollow/{username}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getRemoveFollow(@PathVariable("username") String username, ModelMap model) {
		Follow _follow = followService.findFollow(getPrincipal(), username);
		followService.deleteFollow(_follow);
		return messageSource.getMessage("message.success.remove.follow", null, LocaleContextHolder.getLocale());
	}

	@ModelAttribute("profile")
	public User authenticateUser() {
		User user = userService.findUserByUsername(getPrincipal());
		return user;
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
