/* Author: Florian Rampp */

var roomr = (function() {
	var my = {};
	
	var modules = {};
	
	my.addTypingFinishedCallback = function(elements, callback, doneTypingTimeout) {
		var targets = $(elements);
		var typingTimer;               // timer identifier
		
		if (typeof doneTypingTimeout == 'undefined') {
			doneTypingTimeout = 200;
		}

		targets.each(function() {
			$(this).data('oldVal', $(this).val());
			$(this).bind("propertychange keyup input paste", function() {
				var changed = false;
				targets.each(function() {
					changed = changed || $(this).data('oldVal') != $(this).val();
				});
				
				if (changed) {
					targets.each(function() {
						$(this).data('oldVal', $(this).val());
					});

					clearTimeout(typingTimer);
					typingTimer = setTimeout(callback, doneTypingTimeout);
				}
			});
		});
	}
	
	my.addModule = function(pageId, module) {
		modules[pageId] = module;
	};
	
	my.init = function(pageId) {
		if (pageId in modules) {
			modules[pageId].init();
		}
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
		$('#loading').show();
		$('.offer').hide();
		
		// omit empty input fields
		$('.offers').load('/offers', $(':input[value]', $('#search_offers_form')).serialize(), function(responseText, textStatus, XMLHttpRequest) {
			$('.offers .score:not(.undefined)').each(function() {
				$(this).css('background-color', calcColor($(this).text().replace('%', '')));

				// without the relative property, the tooltip is not displayed
				$(this).tooltip( { relative: true });
			});
	  
			$('#loading').hide();
			$('.offer').fadeIn(500);
		});
	}
	
	my.init = function() {
		$(':input:not(:checkbox, :radio)', $('#search_offers_form')).each(function() {
			roomr.addTypingFinishedCallback(this, updateOfferResultList);
		});
		$(':checkbox, :radio', $('#search_offers_form')).each(function() {
			   $(this).change(function() {
				   updateOfferResultList()
			   });
		});
		
		// update list to account for initial content of the field
		updateOfferResultList();
	};
	
	roomr.addModule('search_offer', my);
	return my;
}());

roomr.requestForm = (function() {
	var my = {};

	my.init = function() {
		$("#create_request").formwizard({
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
	
	roomr.addModule('create_request', my);
	return my;
}());


roomr.createOffer = (function() {
	var my = {};
	
	var offerMap;
	var geocoder;
	var addressMarker;
	
	var street;
	var streetNumber;
	var zipCode;
	var city;

	geocodeAddress = function() {
		var address = street.val() + ' ' + streetNumber.val() + ', ';
		if (zipCode.val() != '' || city.val() != '') {
			address = address + zipCode.val() + ' ' + city.val();
		} else {
			address = address + 'München';
		}
		
		new google.maps.Geocoder().geocode({ 'address': address, 'region': 'de' }, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				offerMap.setCenter(results[0].geometry.location);
				offerMap.panToBounds(results[0].geometry.viewport);
				
			    addressMarker = addressMarker || new google.maps.Marker({
			        map: offerMap
			    });
			    addressMarker.setPosition(results[0].geometry.location);
			} else {
				alert("Geocode was not successful for the following reason: " + status);
			}
		});
	};
	
	initializeMap = function(mapCanvas) {
		geocoder = new google.maps.Geocoder();
	    var latlng = new google.maps.LatLng(48.1505, 11.5586);
	    var options = {
	      zoom: 8,
	      center: latlng,
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	    offerMap = new google.maps.Map(mapCanvas, options);
	    
	    street = $('.adr .street');
	    streetNumber = $('.adr .streetnumber');
	    zipCode = $('.adr .postal-code');
	    city = $('.adr .locality');
	    
    	roomr.addTypingFinishedCallback([street, streetNumber, zipCode, city], geocodeAddress, 1000);
	};
	
	my.init = function() {
		var minAgeInput = $('#create_offer #ageRangeSelect input:eq(0)');
		var maxAgeInput = $('#create_offer #ageRangeSelect input:eq(1)');
		$('#create_offer #ageRangeSelect').append('<div class="slider">');

		$('#create_offer #ageRangeSelect div.slider').slider({
			range: true,
			min: 0,
			max: 99,
			values: [ minAgeInput.val(), maxAgeInput.val()],
			slide: function( event, ui ) {
				minAgeInput.val(ui.values[0]);
				maxAgeInput.val(ui.values[1]);
			}
		});
		
		var offerFormCanvas = $("#map_canvas");
		if (offerFormCanvas.length == 1) {
			initializeMap(offerFormCanvas.get(0));
		}
	};
	
	roomr.addModule('create_offer', my);
	return my;
}());