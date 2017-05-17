package com.cruz.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {
	@Autowired
	MessageSource messageSource;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) {
		if (exception.getClass().equals(NullPointerException.class)) {
			return new ModelAndView("error", "message",
					messageSource.getMessage("error.null", null, LocaleContextHolder.getLocale()));
		}
		System.err.println("" + exception.getMessage());
		return new ModelAndView("error", "message", exception.getMessage());
	}

}
