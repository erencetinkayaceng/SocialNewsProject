$(document).ready(function() {
	var toast = document.getElementById("snackbar").innerHTML;
	if (toast.length > 0) {
		toastMessage();
	}

	$("#toTop").click(function() {
		$("html, body").animate({
			scrollTop : "0"
		}, 500);
		return false;
	});
});
var contextRoot = 'http://localhost:8080/controller/';

function toastMessage() {
	var x = document.getElementById("snackbar");
	x.className = "show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 4000);
}

$(window).scroll(function() {
	if ($(this).scrollTop() > 500)
		$("#toTop").fadeIn(500);
	else
		$("#toTop").fadeOut(500);
});

function newFollow(username) {
	$.ajax({
		type : "GET",
		url : contextRoot + 'follow/newFollow/' + username,
		success : function(data) {
			$('#follow-' + username).load(
					document.URL + ' #follow-' + username,
					function(data) {
						var datapart = $(data).find('#follow-' + postlink)
								.html();
						$('#follow-' + postlink).html(datapart);
					});
			$('#snackbar').html(data);
			toastMessage();
		}
	});
}

function removeFollow(username) {
	$.ajax({
		type : "GET",
		url : contextRoot + 'follow/removeFollow/' + username,
		success : function(data) {
			$('#follow-' + username).load(
					document.URL + ' #follow-' + username,
					function(data) {
						var datapart = $(data).find('#follow-' + postlink)
								.html();
						$('#follow-' + postlink).html(datapart);
					});
			$('#snackbar').html(data);
			toastMessage();
		}
	});
}

function reservednewsSave(postlink) {
	$.ajax({
		type : "GET",
		url : contextRoot + 'reservednews/saveReservedNews/' + postlink,
		success : function(data) {
			$('#post-operation-button-group-' + postlink).load(
					document.URL + ' #post-operation-button-group-' + postlink,
					function(data) {
						var datapart = $(data).find(
								'#post-operation-button-group-' + postlink)
								.html();
						$('#post-operation-button-group-' + postlink).html(
								datapart);
					});
			$('#snackbar').html(data);
			toastMessage();
		}
	});
}

function pinnewsSave(postlink) {
	$.ajax({
		type : "GET",
		url : contextRoot + 'pinnews/savePinNews/' + postlink,
		success : function(data) {
			$('#post-operation-button-group-' + postlink).load(
					document.URL + ' #post-operation-button-group-' + postlink,
					function(data) {
						var datapart = $(data).find(
								'#post-operation-button-group-' + postlink)
								.html();
						$('#post-operation-button-group-' + postlink).html(
								datapart);
					});
			$('#snackbar').html(data);
			toastMessage();
		}
	});
}

function pinnewsRemove(postlink) {
	$.ajax({
		type : "GET",
		url : contextRoot + 'pinnews/removePinNews/' + postlink,
		success : function(data) {
			pinnewsHide(postlink);
			$('#post-operation-button-group-' + postlink).load(
					document.URL + ' #post-operation-button-group-' + postlink,
					function(data) {
						var datapart = $(data).find(
								'#post-operation-button-group-' + postlink)
								.html();
						$('#post-operation-button-group-' + postlink).html(
								datapart);
					});
			$('#snackbar').html(data);
			toastMessage();
		}
	});
}
/* pinnews sayfasında silinen pinnewi gizlemek */
function pinnewsHide(postlink) {
	var str = window.location.href;
	var hide = str.match(/pinnews/g);
	if (hide != null) {
		$('#pnnews-' + postlink).hide();
	}
}

function likeSave(postlink) {
	$.ajax({
		type : "GET",
		url : contextRoot + 'like/createLike/' + postlink,
		success : function(data) {
			$('#postFooter-' + postlink).load(
					document.URL + ' #postFooter-' + postlink,
					function(data) {
						var datapart = $(data).find('#postFooter-' + postlink)
								.html();
						$('#postFooter-' + postlink).html(datapart);
					});
		}
	});
}

function likeRemove(postlink) {
	$.ajax({
		type : "GET",
		url : contextRoot + 'like/removeLike/' + postlink,
		success : function(data) {
			$('#postFooter-' + postlink).load(
					document.URL + ' #postFooter-' + postlink,
					function(data) {
						var datapart = $(data).find('#postFooter-' + postlink)
								.html();
						$('#postFooter-' + postlink).html(datapart);
					});
		}
	});
}
