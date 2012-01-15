<%@ page pageEncoding="utf-8" %>

<html>
<head>
<title>create offer</title>
<link rel="stylesheet" href="/css/ui-lightness/jquery-ui-1.8.16.custom.css" type="text/css" />
<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script src="/js/jquery-ui-1.8.16.custom.min.js"></script>
<script src="/js/jquery.form.js"></script>
<script src="/js/jquery.validate.js"></script>
<script src="/js/jquery.form.wizard-min.js"></script>
<script src="/js/jquery.ba-bbq.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#createOfferForm").formwizard({
            formPluginEnabled: false,
            validationEnabled: true,
            historyEnabled: true,
            focusFirstInput : true,
            formOptions : {
                success: function(data) {
                    $("#status").fadeTo(500,1,function(){
                        $(this).html("You are now registered!").fadeTo(5000, 0);
                    })
                },
                beforeSubmit: function(data){
                    $("#data").html("data sent to the server: " + $.param(data));
                },
                dataType: 'json',
                resetForm: true
            }
        });

		$(function() {
			var dates = $( "#fromDate, #toDate" ).datepicker({
				defaultDate: "+1w",
				changeMonth: false,
				numberOfMonths: 2,
				onSelect: function( selectedDate ) {
					var option = this.id == "fromDate" ? "minDate" : "maxDate",
						instance = $( this ).data( "datepicker" ),
						date = $.datepicker.parseDate(
							instance.settings.dateFormat ||
							$.datepicker._defaults.dateFormat,
							selectedDate, instance.settings );
					dates.not( this ).datepicker( "option", option, date );
				}
			});
		});
		
	});
</script>
</head>
<body>
	<h1>Create offer</h1>
	<form id="createOfferForm" method="post" action="offers">
		<fieldset id="location" class="step">
			<label for="quarter">Quarter</label>
			<input id="quarter" name="quarter" type="text">
			<fieldset id="address">
				<label for="street">Street</label>
				<input id="street" name="street" type="text">
				<label for="streetNumber">Street number</label>
				<input id="streetNumber" name="streetNumber" type="number">
				<label for="zipCode">ZIP code</label>
				<input id="zipCode" name="zipCode" type="number">
				<label for="city">City</label>
				<input id="city" name="city" type="text">
			</fieldset>
		</fieldset>
		<fieldset id="roomDetails" class="step">
			<label for="fromDate">From</label>
			<input id="fromDate" name="fromDate" type="date">
			<label for="toDate">To</label>
			<input id="toDate" name="toDate" type="text">
			<label for="rent">Rent</label>
			<input id="rent" name="rent" type="text">
		</fieldset>
		<input type="submit">
		<input type="reset">
	</form>
</body>
</html>
