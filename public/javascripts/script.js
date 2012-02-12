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
	
	function updateOfferResultList() {
	   $('.offers').load('/offers', $('#search_offers_form').serialize(), function(responseText, textStatus, XMLHttpRequest) {
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