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
	   $('.offers').load('/offers', $('#search_offers_form').serialize());
	}
	
	$('#search_offers_form input').each(function() {
		   // Save current value of element
		   $(this).data('oldVal', $(this).val());

		   // Look for changes in the value
		   $(this).bind("propertychange keyup input paste", function(event){
			   // If value has changed...
			   if ($(this).data('oldVal') != $(this).val()) {
				   // Updated stored value
				   $(this).data('oldVal', $(this).val());

				   // TODO: replace the link here
				   updateOfferResultList();
			   }
		   });
	 });
	
	// update list to account for initial content of the field
	updateOfferResultList();
	 
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