/* Author: Florian Rampp */

var roomr = (function() {
	var my = {};
	
	var modules = [];
	
	my.addModule = function(module) {
		modules.push(module);
	};
	
	my.init = function() {
		$.each(modules, function(idx, module){
			module.init();
		});
	};
	
	return my;
}());

roomr.instantSearch = (function() {
	var my = {};
	
	function calcColor(p) {
		var red = p < 50 ? 255 : Math.round(256 - (p - 50) * 5.12);
		var green = p > 50 ? 255 : Math.round((p) * 5.12);
		return "rgb(" + red + "," + green + ", 0)";
	}
	
	function updateOfferResultList() {
		// omit empty input fields
	   $('.offers').load('/offers', $('#search_offers_form :input[value]').serialize(), function(responseText, textStatus, XMLHttpRequest) {
		   $('.offers .score:not(.undefined)').each(function() {
			   $(this).css('background-color', calcColor($(this).text().replace('%', '')));
		   });
		   
		   $('.offer').hide().fadeIn(500);
	   });
	}
	
	my.init = function() {
		$('#search_offers_form input').each(function() {
			var typingTimer;               // timer identifier
			var doneTypingInterval = 200;  // time in ms, 5 second for example
			   $(this).data('oldVal', $(this).val());
	
				// start the countdown
			   $(this).bind("propertychange keyup input paste", function() {
				   if ($(this).data('oldVal') != $(this).val()) {
					   $(this).data('oldVal', $(this).val());
	
				       clearTimeout(typingTimer);
				       typingTimer = setTimeout(doneTyping, doneTypingInterval);
				   }
			   });
		
			   // user is "finished typing," do something
			   function doneTyping() {
					updateOfferResultList();
			   }
		});
		$('#search_offers_form select').each(function() {
				// start the countdown
			   $(this).change(function() {
				   updateOfferResultList()
			   });
		});
		
		
		// update list to account for initial content of the field
		updateOfferResultList();
	};
	
	roomr.addModule(my);
	return my;
}());

roomr.requestForm = (function() {
	var my = {};

	my.init = function() {
		$("#create_request_form").formwizard({
			formPluginEnabled : false,
			validationEnabled : false,
			historyEnabled : true,
			focusFirstInput : true,
			formOptions : {
				success : function (data) {
					$("#status").fadeTo(500, 1, function () {
						$(this).html("You are now registered!").fadeTo(5000, 0);
					});
				},
				beforeSubmit : function (data) {
					$("#data").html("data sent to the server: " + $.param(data));
				},
				dataType : 'json',
				resetForm : true
			}
		});

		$(function () {
			var dates = $("#fromDate, #toDate").datepicker({
				defaultDate : "+1w",
				changeMonth : false,
				numberOfMonths : 2,
				onSelect : function (selectedDate) {
					var option = this.id === "fromDate" ? "minDate" : "maxDate", instance = $(this).data("datepicker"), date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
					dates.not(this).datepicker("option", option, date);
				}
			});
		});
	};
	
	roomr.addModule(my);
	return my;
}());


roomr.createOffer = (function() {
	var my = {};

	my.init = function() {
		
		var minAgeInput = $('#create_offer_form #ageRangeSelect input:eq(0)');
		var maxAgeInput = $('#create_offer_form #ageRangeSelect input:eq(1)');
		$('#create_offer_form #ageRangeSelect').append('<div class="slider">');

		$('#create_offer_form #ageRangeSelect div.slider').slider({
			range: true,
			min: 0,
			max: 99,
			values: [ minAgeInput.val(), maxAgeInput.val()],
			slide: function( event, ui ) {
				minAgeInput.val(ui.values[0]);
				maxAgeInput.val(ui.values[1]);
			}
		});
		
	};
	
	roomr.addModule(my);
	return my;
}());