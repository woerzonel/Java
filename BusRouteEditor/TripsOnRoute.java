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
 * Name: Nick Scharrer
 * Created: 4/12/2018
 */
package teamEtransitsystem;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.Collection;
import java.util.List;

/**
 * @author scharrernf
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class TripsOnRoute {

    private ListMultimap<String, Trip> tripsOnRoute;
    private ListMultimap<String, Route> routesOnTrip;

	/**
	 * Creates list of tripsOnRoute, and routesOnTrip
	 */
	public TripsOnRoute(){
        tripsOnRoute = ArrayListMultimap.create();
        routesOnTrip = ArrayListMultimap.create();
	}


	/**
	 * Add a trip with a corresponding routeID (relation) if not already existing
	 *
	 * @param routeID String routeID of the requested route
	 * @param trip Trip object
	 */
	public void addTripOnRoute(String routeID, Trip trip) {
	    if (!tripsOnRoute.containsEntry(routeID, trip)) {
	        tripsOnRoute.put(routeID, trip);
        }
    }

	/**
	 * Add a route with a corresponding tripID (relation) if not already existing
	 *
	 * @param tripID String tripID of the requested trip
	 * @param route Route object
	 */
	public void addRouteOnTrip(String tripID, Route route) {
        if (!routesOnTrip.containsEntry(tripID, route)) {
            routesOnTrip.put(tripID, route);
        }
    }

	/**
	 * Searches for trips with a corresponding routeID
	 *
	 * @param routeID Requested routeID
	 * @return tripsOnRoute trips with that routeID
	 */
	public List<Trip> searchForTripsOnRoute(String routeID) {
		return tripsOnRoute.get(routeID);
	}


	/**
	 * Deletes a  specific trip that corresponds to a particular routeID
	 *
	 * @param routeID the routeID which contains the trip(s) to be deleted
	 * @param trip the trip to be deleted
	 */
    public void deleteTripOnRoute(String routeID, Trip trip) {
		if(tripsOnRoute.containsEntry(routeID, trip)) {
			tripsOnRoute.remove(routeID, trip);
		}
	}

	/**
	 * Deletes a  specific route that corresponds to a particular tripID
	 *
	 * @param tripID the tripID which contains the route(s) to be deleted
	 * @param route the route to be deleted
	 */
	public void deleteRouteOnTrip(String tripID, Route route) {
		if (routesOnTrip.containsEntry(tripID, route)) {
			routesOnTrip.remove(tripID, route);
		}
	}

	/**
	 * Searches for routes with a corresponding tripID
	 *
	 * @param tripID Requested tripID
	 * @return routesOnTrip routes with that tripID
	 */
	public Collection<Route> searchForRoutesOnTrip(String tripID) {
	    return routesOnTrip.get(tripID);
    }

}