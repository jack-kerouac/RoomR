/* Author: Florian Rampp */

$(document).ready(function () {
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
	
	var updateOfferResultList = function() {
	   $('.offers').load('/offers', $('#search_offers_form').serialize(), function(responseText, textStatus, XMLHttpRequest) {
		   $('.offer').hide().fadeIn(500);
	   });
	}
	
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
});


var demo = {
	common : {
		init : function () {
			//initialize
		},
		finalize : function () {
			//finalize
		},
		config : {
			prop : "my value",
			constant : "42"
		}
	},
	mapping : {
		init : function() {
			//create a map
		},
		geolocate : function() {
			//geolocation is cool
		},
		geocode : function() {
			//look up an address or landmark
		},
		drawPolylines : function() {
			//draw some lines on a map
		},
		placeMarker : function() {
			//place markers on the map
		}
	}
}