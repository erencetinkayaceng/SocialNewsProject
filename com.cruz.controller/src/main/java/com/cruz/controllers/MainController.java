package com.cruz.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.PageHolder;
import com.cruz.model.Post;
import com.cruz.model.user.User;
import com.cruz.service.PostService;
import com.cruz.service.StrategyMainPostService;
import com.cruz.service.UserService;
import com.cruz.strategy.StrategyContext;
import com.cruz.strategy.Impl.StrategyFollowImpl;
import com.cruz.strategy.Impl.StrategyPopularPostImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/main")
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	StrategyContext strategyContext;
	@Autowired
	StrategyMainPostService strategyMainPostService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMainPostByStrategyType(ModelMap model,
			@RequestParam(value = "strategyType", defaultValue = "follow") String strategyType,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
			@RequestParam(value = "totalCount", defaultValue = "0") int totalCount) {

		setStrategyContextByStrategyType(strategyType);
		List<Post> resultList = strategyContext.fecthAllPostByStrategyType(pageNum * pageSize, pageSize);
		PageHolder<?> pageHolder = new PageHolder<>(pageNum, pageSize, strategyContext.getTotalCount(totalCount),
				resultList, "main/");

		model.addAttribute("strategyType", strategyType);
		model.addAttribute("pageHolder", pageHolder);
		return "main/main";
	}

	private void setStrategyContextByStrategyType(String strategyType) {
		switch (strategyType) {
		case "follow":
			strategyContext.setStrategyType(new StrategyFollowImpl(strategyMainPostService));
			break;
		case "popularPost":
			strategyContext.setStrategyType(new StrategyPopularPostImpl(strategyMainPostService));
			break;
		default:
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
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
