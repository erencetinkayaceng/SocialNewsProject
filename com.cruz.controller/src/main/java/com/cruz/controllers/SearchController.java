package com.cruz.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cruz.factory.Search;
import com.cruz.factory.SearchFactory;
import com.cruz.model.PageHolder;
import com.cruz.model.user.User;
import com.cruz.service.PostService;
import com.cruz.service.UserService;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	@Autowired
	UserService userService;
	@Autowired
	SearchFactory searchFactory;
	@Autowired
	PostService postService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/{searchType}", method = RequestMethod.GET)
	public String getSearchBySearchType(@PathVariable("searchType") String searchType, Model model,
			@RequestParam("searchWords") String searchWords,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		Search search = searchFactory.createSearchBySearchType(searchType);
		List<?> resultList = search.doSearchByPage(searchWords, pageNum * pageSize, pageSize);
		PageHolder<?> pageHolder = new PageHolder<>(pageNum, pageSize, search.getTotalCount(searchWords, totalCount),
				resultList, "search/" + searchType);

		model.addAttribute("searchWords", searchWords);
		model.addAttribute("searchType", searchType);
		model.addAttribute("pageHolder", pageHolder);
		return "search/search";
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
