package com.cruz.controllers;

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

import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.PageHolder;
import com.cruz.model.Post;
import com.cruz.model.ReservedNews;
import com.cruz.model.user.User;
import com.cruz.service.PostService;
import com.cruz.service.ReservedNewsService;
import com.cruz.service.UserService;
import com.cruz.utils.GetLocalDateTime;

@Controller
@RequestMapping(value = "/reservednews")
public class ReservedNewsController {

	private static final Logger logger = LoggerFactory.getLogger(ReservedNewsController.class);
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	ReservedNewsService reservedNewsService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String getAllReservedNews(ModelMap model, @ModelAttribute("operationmessage") String operationmessage,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		if (totalCount == 0) {
			totalCount = reservedNewsService.findAllReservedNewsByUsername(getPrincipal()).size();
		}
		List<ReservedNews> reservedNewsList = reservedNewsService.findAllReservedNewsByUsernameAndPage(getPrincipal(),
				pageNum * pageSize, pageSize);
		PageHolder<ReservedNews> pageHolder = new PageHolder<ReservedNews>(pageNum, pageSize, totalCount,
				reservedNewsList, "reservednews/detail");

		model.addAttribute("pageHolder", pageHolder);
		model.addAttribute("operationmessage", operationmessage);
		return "/user/reservednews/viewReservedNews";
	}

	@PreAuthorize("@mySecurityService.canAccessReservedNewsSaveByPostLink(#postlink)")
	@RequestMapping(value = "/saveReservedNews/{postlink}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getSaveReservedNews(@PathVariable("postlink") String postlink, ModelMap model) {
		Post post = postService.findPostByPostLink(postlink);
		ReservedNews rn = reservedNewsService.findReservedNewsByPostIdAndUsername(post.getId(), getPrincipal());
		if (rn == null) {
			ReservedNews reservedNews = new ReservedNews();
			reservedNews.setAddDate(GetLocalDateTime.getCurrentTimeStamp());
			reservedNewsService.saveReservedNews(reservedNews);

			reservedNews.setPost(post);
			reservedNews.setUser(userService.findUserByUsername(getPrincipal()));
			reservedNewsService.updateReservedNews(reservedNews);
		} else if (!rn.isEnabled()) {
			rn.setEnabled(!rn.isEnabled());
			reservedNewsService.updateReservedNews(rn);
		}

		return messageSource.getMessage("message.success.create.reservednew", null, LocaleContextHolder.getLocale());
	}

	@PreAuthorize("@mySecurityService.canAccessReservedNewsRemove(#rnid)")
	@RequestMapping(value = "/removeReservedNews/{rnid}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getRemoveReservedNews(@PathVariable("rnid") String rnid, ModelMap model) {
		Long _id = 0L;
		try {
			_id = Long.parseLong(rnid);
		} catch (Exception e) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		ReservedNews reservedNews = reservedNewsService.findReservedNewsById(_id);
		reservedNewsService.deleteReservedNews(reservedNews);

		return messageSource.getMessage("message.success.remove.reservednew", null, LocaleContextHolder.getLocale());
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
