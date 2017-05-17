package com.cruz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cruz.exception.NullorInvalidPathVariableException;
import com.cruz.model.Comment;
import com.cruz.model.Follow;
import com.cruz.model.Like;
import com.cruz.model.PinNews;
import com.cruz.model.Post;
import com.cruz.model.ReservedNews;
import com.cruz.service.CommentService;
import com.cruz.service.FollowService;
import com.cruz.service.LikeService;
import com.cruz.service.PinNewsService;
import com.cruz.service.PostService;
import com.cruz.service.ReservedNewsService;

@Component("mySecurityService")
public class CustomAuthenticationService {

	@Autowired
	FollowService followService;
	@Autowired
	PostService postService;
	@Autowired
	ReservedNewsService reservedNewsService;
	@Autowired
	PinNewsService pinNewsService;
	@Autowired
	CommentService commentService;
	@Autowired
	LikeService likeService;
	@Autowired
	MessageSource messageSource;

	public boolean canAccessUserEdit(String username) {
		return username.equals(getPrincipal());
	}

	public boolean canAccessPostEditByPostLink(String postLink) {
		Post post = postService.findPostByPostLink(postLink);
		return post.getUser().getUsername().equals(getPrincipal());
	}

	// Reserved news begin
	public boolean canAccessReservedNewsSaveByPostLink(String postLink) {
		Post post = postService.findPostByPostLink(postLink);
		/* Post sahibi ise post ayırmaya izin vermiyoruz */
		if (getPrincipal().equals(post.getUser().getUsername())) {
			return false;
		}

		ReservedNews rnews = reservedNewsService.findReservedNewsByPostIdAndUsername(post.getId(), getPrincipal());
		if (rnews == null) {
			return true;
		}
		return !rnews.isEnabled();
	}

	public boolean canAccessReservedNewsRemove(String id) {
		Long _id = 0L;
		try {
			_id = Long.parseLong(id);
		} catch (Exception e) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		ReservedNews rnews = reservedNewsService.findReservedNewsById(_id);
		if (rnews == null) {
			return false;
		}
		return rnews.getUser().getUsername().equals(getPrincipal()) && rnews.isEnabled();
	}

	// Pin news begin
	public boolean canAccessPinNewsSaveByPostLink(String postLink) {
		Post post = postService.findPostByPostLink(postLink);
		/* Post sahibi ise post pinlemeye izin vermiyoruz */
		if (getPrincipal().equals(post.getUser().getUsername())) {
			return false;
		}

		PinNews pnews = pinNewsService.findPinNewsByPostIdAndUsername(post.getId(), getPrincipal());
		if (pnews == null) {
			return true;
		}
		return !pnews.isEnabled();
	}

	public boolean canAccessPinNewsRemoveByPostLink(String postlink) {
		if (postlink == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		Post post = postService.findPostByPostLink(postlink);
		PinNews pnews = pinNewsService.findPinNewsByPostIdAndUsername(post.getId(), getPrincipal());
		if (pnews == null) {
			return false;
		}
		return pnews.getUser().getUsername().equals(getPrincipal()) && pnews.isEnabled();
	}

	public boolean isCheckFollow(String followed) {
		Follow follow = followService.findFollow(getPrincipal(), followed);
		if (follow == null) {
			return false;
		}
		return follow.isEnabled();
	}

	public boolean canAccessCommentRemove(String commentID) {
		if (commentID == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		Comment comment;
		try {
			comment = commentService.findComment(Long.parseLong(commentID));
		} catch (Exception e) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}

		return comment.getUser().getUsername().equals(getPrincipal())
				|| comment.getPost().getUser().getUsername().equals(getPrincipal());
	}

	public boolean canAccessLikeCreateByPostLink(String postlink) {
		if (postlink == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		Like like = likeService.findLikeByPostLinkAndUsername(postlink, getPrincipal());

		if (like == null) {
			return true;
		}
		return !like.isEnabled();
	}

	public boolean canAccessLikeRemoveByPostLink(String postlink) {
		if (postlink == null) {
			throw new NullorInvalidPathVariableException(messageSource.getMessage("error.nullorinvalid.pathvariable",
					null, LocaleContextHolder.getLocale()));
		}
		Like like = likeService.findLikeByPostLinkAndUsername(postlink, getPrincipal());

		if (like == null) {
			return false;
		}
		return like.isEnabled();
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
