/*
 * Copyright (C) - 2018 - SE2030 Group E - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by: Nick Scharrer <scharrernf@msoe.edu>,
 * 			   Gabe Woerishofer <woerishofergw@msoe.edu>,
 * 			   Alex Shulta <shultaar@msoe.edu>,
 * 			   Reid Witt <wittrm@msoe.edu>
 * Written: March - May 2018
 */

/*
 * SE2030 - 011
 * Spring 2018
 * Transit System Project
 * Name: Alex Shulta
 * Created: 4/12/2018
 */
package teamEtransitsystem;


import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alex Shulta
 * @version 1.0
 * @created 12-Apr-2018 10:33:09 AM
 */
public class RouteObserver implements Observer {

	private Collection<Route> routes;

	/**
	 * Constructor for RouteObserver, instantiates a new ArrayList to hold routes
	 */
	public RouteObserver(){ routes = new ArrayList<>(); }

	/**
	 * Update the collection of Routes
	 *
	 * @param arg TransitSystem that implements Subject
	 */
	public void update(Subject arg){ routes = ((TransitSystem)arg).getRouteCollection(); }

	public Collection<Route> getRoutes() {
		return routes;
	}

}