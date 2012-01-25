<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>


<html>
<head>
<title>Offers</title>
</head>
<body>
	<h1>Offers for ${seekerId}</h1>

	<display:table name="publisherRoomOffers" id="row">
		<display:column title="time of publication"><joda:format value="${row.publishingDate}" style="SS" /></display:column>
		<display:column title="ID"><a href="/market/offers/${row.offer.id}">${row.offer.id}</a></display:column>
		<display:column property="offer.room.size" />
		<display:column property="offer.pricing.totalCostsPerMonth" />
		<display:column property="offer.flatShare.address.city" />
		<display:column property="offer.flatShare.quarter" />
	</display:table>
<!-- 	<ul> -->
<%-- 		<c:forEach var="publishedOffer" items="${publisherRoomOffers}"> --%>
<%-- 			<li><a href="offers/${publishedOffer.offer.id}">rent: --%>
<%-- 					${publishedOffer.offer.pricing.rentPerMonth}, deposit: --%>
<%-- 					${publishedOffer.offer.pricing.deposit}<br> --%>
<%-- 					${publishedOffer.offer.flatShare.flat.houseType}, --%>
<%-- 					${publishedOffer.offer.flatShare.quarter}, --%>
<%-- 					${publishedOffer.offer.flatShare.flat.floor}, --%>
<%-- 					${publishedOffer.offer.flatShare.flat.size}</a></li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->

</body>
</html>
