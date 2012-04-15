/*****************************************************************************************/
/** ROOMR GLOBAL MODULE ******************************************************************/
/*****************************************************************************************/
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



/*****************************************************************************************/
/** GLOBAL MAPS **************************************************************************/
/*****************************************************************************************/
roomr.maps = (function() {
	var my = {};
	
	my.init = function() {
	};

	my.initializeMap = function(mapCanvas, latlng, zoom) {
		var map;

		var options = {
	      zoom: zoom,
	      center: latlng,
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	    map = new google.maps.Map($(mapCanvas)[0], options);
	    
    	return map;
	}
	
	my.initializeStreetView = function(streetViewCanvas, streetMap, position, pov, panoOptions, positionChangeListener, povChangeListener) {
		var streetView;

    	streetView = new google.maps.StreetViewPanorama(streetViewCanvas.get(0), panoOptions);
    	streetView.setPosition(position);
    	streetView.setPov(pov);
    	
    	streetMap.setStreetView(streetView);
    	if(typeof positionChangeListener != 'undefined') {
	    	google.maps.event.addListener(streetView, 'position_changed', function() {
    			positionChangeListener(streetView.getPosition());
			});
    	}
    	if(typeof povChangeListener != 'undefined') {
	    	google.maps.event.addListener(streetView, 'pov_changed', function() {
    			povChangeListener(streetView.getPov());
			});
    	}
		
    	return streetView;
	}

	my.geocodeAddress = function(address, callback) {
		new google.maps.Geocoder().geocode({ 'address': address, 'region': 'de' }, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				callback(results[0]);
			}
		});
	}
	
	
	
	roomr.addModule('maps', my);
	return my;
}());


/*****************************************************************************************/
/** INSTANT SEARCH ***********************************************************************/
/*****************************************************************************************/
roomr.instantSearch = (function() {
	var my = {};
	
	function calcColor(p) {
		var red = p < 50 ? 255 : Math.round(256 - (p - 50) * 5.12);
		var green = p > 50 ? 255 : Math.round((p) * 5.12);
		return "rgb(" + red + "," + green + ", 0)";
	}
	
	function updateOfferResultList() {
		$('#loading').show();
		
		// omit empty input fields
		$('.offers').load('/offers', $(':input[value]', $('#search_offers_form')).serialize(), function(responseText, textStatus, XMLHttpRequest) {
			$('.offers .score:not(.undefined)').each(function() {
				$(this).css('background-color', calcColor($(this).text().replace('%', '')));

				// without the relative property, the tooltip is not displayed
				$(this).tooltip( { relative: true, offset: [-5, 0] });
			});
	  
			$('#loading').hide();
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
	
	roomr.addModule('search_offers', my);
	return my;
}());


/*****************************************************************************************/
/** REQUEST FORM (DEPRECATED) ************************************************************/
/*****************************************************************************************/
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


/*****************************************************************************************/
/** CREATE OFFER FORM ********************************************************************/
/*****************************************************************************************/
roomr.createOffer = (function() {
	var my = {};
	
	var streetMap;
	var addressMarker;
	var streetView;
	var streetViewEnabled;
	
	var street;
	var streetNumber;
	var zipCode;
	var city;
	
	var lat;
	var lng;
	
	var streetViewCanvas;
	var streetViewOptions;
	
	var displayStreetView;
	var streetViewHeading;
	var streetViewPitch;
	var streetViewZoom;
	
	
	my.init = function() {
		var initialPosition, initialPov, streetViewDisplayChangeListener;
		
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
		
		
		// STREET MAP
		lat = $('#formData_lat');
		lng = $('#formData_lng');
		initialPosition = new google.maps.LatLng(lat.val(), lng.val());
		streetMap = roomr.maps.initializeMap($("#map_canvas"), initialPosition, 10);

		
		// STREET VIEW
    	streetViewCanvas = $("#street_view_canvas");
		displayStreetView = $('#formData_displayStreetView');

		streetViewLat = $('#formData_streetViewLat');
		streetViewLng = $('#formData_streetViewLng');
		streetViewHeading = $('#formData_streetViewHeading');
		streetViewPitch = $('#formData_streetViewPitch');
		streetViewZoom = $('#formData_streetViewZoom');
    	
		initialStreetViewPosition = getStreetViewPositionFromForm();
		initialPov = getPovFromForm();
		streetViewOptions = {
			addressControl : false,
			linksControl : false,
			panControl : true,
			zoomControl : true,
			zoomControlOptions : {
				style : google.maps.ZoomControlStyle.MEDIUM
			},
			enableCloseButton : false,
			visible : true
		};
		
		streetView = roomr.maps.initializeStreetView(streetViewCanvas, streetMap, initialStreetViewPosition, initialPov, streetViewOptions, setStreetViewPositionInForm, setPovInForm);
		
		streetViewDisplayChangeListener = function() {
			if($('input[value="true"]', displayStreetView).prop('checked')) {
				displayStreetView.val('true');
				enableStreetView();
			}
			else {
				displayStreetView.val('false');
				disableStreetView();
			}
		};
		$("input", displayStreetView).change(streetViewDisplayChangeListener);
		streetViewDisplayChangeListener();

		// GEOCODING
		street = $('.adr .street');
		streetNumber = $('.adr .streetnumber');
		zipCode = $('.adr .postal-code');
		city = $('.adr .locality');

		roomr.addTypingFinishedCallback([street, streetNumber, zipCode, city], geocodeAddress, 1000);
		
		prepareAutoCompleteField(zipCode);
		prepareAutoCompleteField(city);
	};
	
	function getStreetViewPositionFromForm() {
		return new google.maps.LatLng(parseFloat(streetViewLat.val()), parseFloat(streetViewLng.val()));
	}

	function setStreetViewPositionInForm(position) {
		streetViewLat.val(position.lat);
		streetViewLng.val(position.lng);
	}
	
	function getPovFromForm() {
		return {
			heading: parseFloat(streetViewHeading.val()),
			pitch: parseFloat(streetViewPitch.val()),
			zoom: parseFloat(streetViewZoom.val())
		};
	}

	function setPovInForm(pov) {
		streetViewHeading.val(pov.heading);
		streetViewPitch.val(pov.pitch);
		streetViewZoom.val(pov.zoom);
	}
	
	function enableStreetView() {
		streetViewEnabled = true;
		$(".disabled_overlay", streetViewCanvas).hide();

		streetView.setOptions(streetViewOptions);
	}
	
	function disableStreetView() {
		var options;
		
		streetViewEnabled = false;
		$(".disabled_overlay", streetViewCanvas).show();
		
		options = {
			addressControl : false,
			linksControl : false,
			panControl : false,
			zoomControl : false
		};
		streetView.setOptions(options);
	}

	function prepareAutoCompleteField(field) {
		$(field).bind('keypress', function() {
			var manuallyEdited = false;
			if ($.trim(field.val()) != '') {
				manuallyEdited = true;
			}
			field.data('manuallyEdited', manuallyEdited);
		});
		field.data('manuallyEdited', false);
	}
	
	function getManualFieldValue(field) {
		if (field.data('manuallyEdited') === true) {
			return $.trim(field.val());
		}
		return '';
	}
	
	function geocodeAddress() {
		var address = street.val() + ' ' + streetNumber.val() + ', ';
		if (getManualFieldValue(zipCode) != '' || getManualFieldValue(city) != '') {
			address = address + getManualFieldValue(zipCode) + ' ' + getManualFieldValue(city);
		}
		
		roomr.maps.geocodeAddress(address, function(bestResult) {
			var panoramaOptions;
			// save coordinates
			lat.val(bestResult.geometry.location.lat());
			lng.val(bestResult.geometry.location.lng());
			
			// auto complete postal code
			$.each(bestResult.address_components, function(index, value) {
				if ($.inArray('postal_code', value.types) != -1 && getManualFieldValue(zipCode) == '') {
					zipCode.val(value.long_name);
				} else if ($.inArray('locality', value.types) != -1 && getManualFieldValue(city) == '') {
					city.val(value.long_name);
				}
			});
			
			// street map
			streetMap.fitBounds(bestResult.geometry.viewport);
			
		    addressMarker = addressMarker || new google.maps.Marker({
		        map: streetMap
		    });
		    addressMarker.setPosition(bestResult.geometry.location);
		    
		    // street view
		    var streetViewService = new google.maps.StreetViewService();
		    var STREETVIEW_MAX_DISTANCE = 100;
		    streetViewService.getPanoramaByLocation(bestResult.geometry.location, STREETVIEW_MAX_DISTANCE, function (streetViewPanoramaData, status) {
		    	switch(status) {
		    	case google.maps.StreetViewStatus.OK:
				    streetView.setPosition(bestResult.geometry.location);
				    streetViewCanvas.show();
				    $('#street_view_error').hide();
		    		break;
			    case google.maps.StreetViewStatus.ZERO_RESULTS:
			    	streetViewCanvas.hide();
					$('#street_view_error').text('Google Street View für diese Position nicht verfügbar!');
			    	$('#street_view_error').show();
		    		break;
			    case google.maps.StreetViewStatus.UNKNOWN_ERROR:
			    	streetViewCanvas.hide();
			    	$('#street_view_error').text('Google Street View Fehler!');
			    	$('#street_view_error').show();
		    		break;
		    	}
		    });
		});
	}
	
	roomr.addModule('create_offer', my);
	return my;
}());


/*****************************************************************************************/
/** VIEW OFFER ***************************************************************************/
/*****************************************************************************************/
roomr.viewOffer = (function() {
	var my = {};

	var streetMap;
	var streetView;
	var addressMarker;

	my.init = function() {
		var flatshareLocation, pov;
		
		$('#apply_button').click(function() {
			$('#apply_dialog').show('slide', { direction: 'up'});
			$('#apply_button').hide();
			
//			$('#apply_dialog').dialog({ modal: true, minHeight: 400, minWidth: 600,
//				buttons: [
//				          {
//							text: "Nachricht versenden!",
//							click: function() {
//								alert('TODO: send message');
//								$(this).dialog("close");
//							}
//				          }
//				          ]});
		});
		
		flatshareLocation = new google.maps.LatLng($("#map_canvas").attr('data-center-lat'), $("#map_canvas").attr('data-center-lng'));
		
		// STREET MAP
		streetMap = roomr.maps.initializeMap($("#map_canvas"), flatshareLocation, 13);

	    addressMarker = new google.maps.Marker({
	        map: streetMap
	    });
	    addressMarker.setPosition(flatshareLocation);
	    
	    // STREET VIEW
    	streetViewCanvas = $("#street_view_canvas");
    	if(streetViewCanvas.size() != 0) {
    		var initialStreetViewPosition = new google.maps.LatLng(
    				parseFloat($("#street_view_canvas").attr('data-pos-lat')),
    				parseFloat($("#street_view_canvas").attr('data-pos-lng')));
			pov = {
				heading : parseFloat($("#street_view_canvas").attr('data-pov-heading')),
				pitch : parseFloat($("#street_view_canvas").attr('data-pov-pitch')),
				zoom : parseFloat($("#street_view_canvas").attr('data-pov-zoom'))
			};
			streetView = roomr.maps.initializeStreetView(streetViewCanvas, streetMap, initialStreetViewPosition, pov);
		}
	};
	
	roomr.addModule('view_offer', my);
	return my;
}());