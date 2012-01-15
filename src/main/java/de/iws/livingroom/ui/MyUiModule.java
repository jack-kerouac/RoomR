package de.iws.livingroom.ui;

import org.zdevra.guice.mvc.MvcModule;

public class MyUiModule extends MvcModule {

	@Override
	protected void configureControllers() {
		// Controllers
		control("/offers").withController(OffersController.class);
		control("/requests").withController(RequestAndSeekerController.class);
		
		// static content
		control("/home").withView("start");
		control("/createOffer").withView("createOffer");
		control("/createRequest").withView("createRequest");
		
		// Views
		bindViewName("createOffer").toJsp("/WEB-INF/content/createOffer.jsp");
		bindViewName("createRequest").toJsp("/WEB-INF/content/createRequest.jsp");
		bindViewName("requests").toJsp("/WEB-INF/content/createRequest.jsp");
		bindViewName("offers").toJsp("/WEB-INF/content/offer.jsp");
		bindViewName("start").toJsp("/WEB-INF/content/start.html");
	}

}