package com.cruz.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.factory.Impl.SearchPostHashTagImpl;
import com.cruz.factory.Impl.SearchPostTitleAndContentImpl;
import com.cruz.factory.Impl.SearchUserImpl;
import com.cruz.service.PostService;
import com.cruz.service.UserService;

@Component("SearchFactory")
public class SearchFactory {
	@Autowired
	MessageSource messageSource;
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;

	public SearchFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Search createSearchBySearchType(String searchType) {
		Search search = null;
		switch (searchType) {
		case "user":
			search = new SearchUserImpl(userService);
			break;
		case "titleAndContent":
			search = new SearchPostTitleAndContentImpl(postService);
			break;
		case "hashTag":
			search = new SearchPostHashTagImpl(postService);
			break;
		default:
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		return search;
	}

}
