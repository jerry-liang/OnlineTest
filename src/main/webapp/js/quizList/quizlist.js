jQuery(document).ready(function() {
	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};
	var categoryId = getUrlParameter('categoryId');
	$('li',$('.ul-capability')).each(function(element){$(this).removeClass('active');})
	if(categoryId) {
		$('.category-' + categoryId).addClass('active');
	} else {
		$('.category-' + '-2').addClass('active');
	}
});

var checkQuizOwner = function(quizId, userName) {
	if(userName == -1) {
		$("#needLoginModal").modal('show');
	} else {
    	var self = this;
    	$.ajax({
    		type: 'post',
    		url: 'checkUserQuizOwner.action',
    		data: {'userName': userName,
    			'quizId': quizId
    		},
    		dataType: 'text',
    		success: function(json) {
    			var obj = $.parseJSON(json);
    			if(obj.quizValid) {
    				alert('已付费');
    				window.location.href = "../quiz/quizDetail.action?quizId=" + quizId;
    			} else {
    				alert('未付费');
    				window.location.href = "../quiz/quizDetail.action?quizId=" + quizId;
    			}
    		},
    		error: function(json) {
    			alert('error2')
    			return false;
    		}
    	})
	}
};

var buyQuiz = function(quizId, userName) {
	if(userName == -1) {
		$("#needLoginModal").modal('show');
	} else {
    	var self = this;
    	$.ajax({
    		type: 'post',
    		url: 'buyQuiz.action',
    		data: {
    			'userName': userName,
    			'quizId': quizId
    		},
    		dataType: 'text',
    		success: function(json) {
    			var obj = $.parseJSON(json);
    			if(obj.quizValid) {
    				$("#buySuccessModal").modal('show');
    			} else {
    				$("#buyFailureModal").modal('show');
    			}
    		},
    		error: function(json) {
    			alert('error2')
    			return false;
    		}
    	})
	}
};

var refresh = function() {
	location.reload();
};

