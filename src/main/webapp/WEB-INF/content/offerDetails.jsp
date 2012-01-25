<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<html>
<head>
<title>Offer ${offer.id}</title>
</head>
<body>
	<h1>${offer.room.size}, ${offer.pricing.totalCostsPerMonth} per month in 
		${offer.flatShare.address.city}, ${offer.flatShare.quarter}</h1>

	<div id="location" class="adr">
		<h2>Location</h2>
		<div class="street-address">${offer.flatShare.address.street} ${offer.flatShare.address.streetNumber}</div>
		<div class="extended-address">${offer.flatShare.quarter}</div>
		<span class="postal-code">${offer.flatShare.address.zipCode}</span> <span class="locality">${offer.flatShare.address.city}</span>
	</div>
	<div id="room">
		<h2>Room</h2>
		<div class="size">${offer.room.size}</div>
		<div class="furniture">${offer.room.furniture}</div>
	</div>
	<div id="flat">
		<h2>Flat</h2>
		<div class="size">${offer.flatShare.flat.size}</div>
		<div class="floorType">${offer.flatShare.flat.floor}</div>
		<div class="houseType">${offer.flatShare.flat.houseType}</div>
		<div class="descriptionEntertainment">${offer.flatShare.flat.descriptionEntertainment}</div>
		<div class="descriptionCommunication">${offer.flatShare.flat.descriptionCommunication}</div>
		<div class="descriptionBathroom">${offer.flatShare.flat.descriptionBathroom}</div>
	</div>
	<div id="freePeriod">
		<h2>Free Period</h2>
		<div>free starting at <joda:format value="${offer.freePeriod.startDate}" style="L-" /><c:if test="${offer.freePeriod.limited}"> until <joda:format value="${offer.freePeriod.endDate}" style="L-" /></c:if></div>
	 </div>
	<div id="pricing">
		<h2>Pricing</h2>
		<div>${offer.pricing.rentPerMonth}</div>
		<div>${offer.pricing.utilitiesPerMonth}</div>
	 	<div>${offer.pricing.deposit}</div>
	 </div>
	 <div id="residents">
		<h2>Residents</h2>
		<c:forEach items="${offer.flatShare.residents}" var="resident">
		<div class="resident">
			<div>${resident.gender}</div>
			<div>${resident.age}</div>
		 	<div>${resident.spokenLanguages}</div>
	 	</div>
	 	</c:forEach>
	 </div>	 
	 <div id="newResidentCriteria">
		<h2>New Resident Criteria</h2>
		<div><c:if test="${offer.newResidentCriteria.genders.maleAllowed}">male</c:if>, <c:if test="${offer.newResidentCriteria.genders.femaleAllowed}">female</c:if></div>
		<div>${offer.newResidentCriteria.ages.startAge.value} - ${offer.newResidentCriteria.ages.endAge.value}</div>
	 </div>
</body>
</html>
