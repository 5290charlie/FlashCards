var t;
jQuery(document).ready(function() {
	if ($("#hasLoaded").val() == 'no') {
		$("#loadData").submit();
	}
	
	$("#testDesc").css('display', 'none');

	var cid = parseInt($("#cid").val());

	$("#content").tabs();
	
	$("#accordion").accordion({
			autoHeight: false,
			active: (cid-1)
	});
		
	$("#createCard, #createCategory").dialog({
		autoOpen: false,
		draggable: false,
		modal: true,
		resizable: false,
		width: 500,
		buttons: {
			"Create": function() {
				$(this).submit();
			},
			"Cancel": function() {
				$(this).dialog('close');
			}
		}
	});
	
	showMsgs();
	
	$(".button").button();
});

function showMsgs() {
	$("#msgs").css('display', 'block');
	t = setTimeout("clearMsgs()", 5000);
}

function clearMsgs() {
	$("#msgs").html('');
	$("#msgs").css('display', 'none');
 	clearTimeout(t);
}

function showDescription() {
	$("#testTitle").css('display','none');
	$("#testDesc").css('display','block');
	$html = "click card to show title";
	$("#hint").html($html);
}

function showTitle() {
	$("#testDesc").css('display','none');
	$("#testTitle").css('display','block');
	$html = "click card to show description";
	$("#hint").html($html);
}

function createCat() {
	$("#createCategory").dialog('open');
}

function newCard() {
	$("#createCard").dialog('open');	
}

function submitEditCard() {
	$("#editCard").submit();
}

function confirmResetData() {
	$orig = '<span onclick="confirmResetData()" class="button">Reset</span>';
	$conf = 'Are you sure? <span class="yes">Yes</span> <span class="no">No</span>';
	$("#resetDataSubmit").html($conf);
	$("#resetDataSubmit .yes, #resetDataSubmit .no").button();
	$("#resetDataSubmit .yes").click(function() {
		$("#resetData").submit();
	});
	$("#resetDataSubmit .no").click(function() {
		$("#resetDataSubmit").html($orig);
		$("#resetDataSubmit .button").button();
	});
}

function confirmRemoveCat(cid) {
	$orig = '<span onclick="confirmRemoveCat(' + cid + ');" class="button">Remove Category</span>';
	$conf = 'Are you sure? <span class="yes">Yes</span> <span class="no">No</span>';
	$("#removeCategorySubmit-" + cid).html($conf);
	$("#removeCategorySubmit-" + cid + " .yes, #removeCategorySubmit-" + cid + " .no").button();
	$("#removeCategorySubmit-" + cid + " .yes").click(function() {
		$("#removeCategory-" + cid).submit();
	});
	$("#removeCategorySubmit-" + cid + " .no").click(function() {
		$("#removeCategorySubmit-" + cid).html($orig);
		$("#removeCategorySubmit-" + cid + " .button").button();
	});
}

function confirmRemoveCard(cid) {
	$orig = '<span onclick="confirmRemoveCard(' + cid + ');" class="button">Remove Card</span>';
	$conf = 'Are you sure? <span class="yes">Yes</span> <span class="no">No</span>';
	$("#removeCardSubmit-" + cid).html($conf);
	$("#removeCardSubmit-" + cid + " .yes, #removeCardSubmit-" + cid + " .no").button();
	$("#removeCardSubmit-" + cid + " .yes").click(function() {
		$("#removeCard-" + cid).submit();
	});
	$("#removeCardSubmit-" + cid + " .no").click(function() {
		$("#removeCardSubmit-" + cid).html($orig);
		$("#removeCardSubmit-" + cid + " .button").button();
	});
}

function activatePlaceholder() {
	$(".input").val(function() {
		return $(this).attr('placeholder');
	}); 
	
	$(".input").focus(function() {
		$val = $(this).val();
		$ph = $(this).attr('placeholder');
		if ($val == $ph) $(this).val('');
			$(this).css('opacity', '1');
	}); 
	
	$(".input").click(function() {
		$val = $(this).val();
		$ph = $(this).attr('placeholder');
		if ($val == $ph) $(this).val('');
			$(this).css('opacity', '1');
	}); 
	
	$(".input").blur(function() {
		$val = $(this).val();
		$ph = $(this).attr('placeholder');
		if ($val == '') $(this).val($ph);
			$(this).css('opacity', '.5');
	});
}