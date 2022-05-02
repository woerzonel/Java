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
import java.util.Collections;

/**
 * @author Alex Shulta
 * @version 1.0
 * @created 12-Apr-2018 10:33:09 AM
 */
public class Routes {

	private ArrayList<Route> routes;

	/**
	 * Constructor for Routes, instantiates empty ArrayList to hold all Route objects
	 */
	public Routes(){ routes = new ArrayList<>();	}

	public void sortRoutes() {
		Collections.sort(routes);
	}

	/**
	 * Add a Route to the Collection of all routes
	 * @param route Route object with populated data
	 */
	public void addRoute(Route route){
        routes.add(route);
	}

	/**
	 * Removes a route
	 * @param route The route to be removed
	 */
	public void deleteRoute(Route route) {
		routes.remove(route);
	}

	/**
	 * Gets all routes
	 * @return All the routes to be returned
	 */
	public Collection<Route> getAllRoutes(){
		return routes;
	}

	/**
	 * Searches for specific routes using a routeID
	 *
	 * @param routeID The ID of the route of type string
	 */
	public Route searchRoute(String routeID){
		return routes.get(binarySearch(routes, 0, (routes.size() - 1), routeID));
	}

	/**
	 * Recursive binary search function to return the location of the routeID.
	 *
	 * @param routes ArrayList of routes
	 * @param l int
	 * @param r int
	 * @param routeID the routeID used in searching
	 * @return int Location of the routeID
	 */
	private int binarySearch(ArrayList<Route> routes, int l, int r, String routeID) {
		if (r >= l) {
			int mid = l + (r - l)/2;

			if (routes.get(mid).getRouteID().compareTo(routeID) == 0) {
				return mid;
			}

			if (routes.get(mid).getRouteID().compareTo(routeID) > 0) {
				return binarySearch(routes, l, mid - 1, routeID);
			}

			return binarySearch(routes, mid+1, r, routeID);
		}

		return -1;
	}

}