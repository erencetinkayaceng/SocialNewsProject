package com.cruz.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HashtagValidator implements ConstraintValidator<ValidHashtag, String>{

	private Pattern pattern;
	private Matcher matcher;
	private static final String HASHTAG_PATTERN = "^[A-Za-z0-9-\\+]*";
	@Override
	public void initialize(ValidHashtag arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String tag, ConstraintValidatorContext arg1) {
		// TODO Auto-generated method stub
		pattern = Pattern.compile(HASHTAG_PATTERN);
		matcher = pattern.matcher(tag);
		return matcher.matches();
	}

}
