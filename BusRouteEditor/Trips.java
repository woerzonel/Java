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


import java.util.*;

/**
 * @author Nick Scharrer (scharrernf@msoe.edu)
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class Trips {

	private ArrayList<Trip> trips;

	/**
	 * Constructor for Trips, instantiates an empty ArrayList to hold all Trip objects
	 */
	public Trips(){
		trips = new ArrayList<>();
	}

	/**
	 * Searches trips within the collection
	 */
	public void sortTrips() {
		Collections.sort(trips);
	}

	/**
	 * Add a Trip object to collection of all Trips
	 * @param trip populated Trip object
	 */
	public void addTrip(Trip trip){
		trips.add(trip);
	}

	/**
	 * Gets all trips
	 *
	 * @return all trips to be returned
	 */
	public Collection<Trip> getAllTrips(){
		return trips;
	}


	/**
	 * Makes a call to the binary search function to search for a trip using a tripID
	 *
	 * @param tripID the particular tripID used in searching
	 * @return The location of the trip corresponding to the tripID
	 */
	public Trip searchTrip(String tripID) {
		return trips.get(binarySearch(trips, 0, trips.size()-1, tripID));
	}

	/**
	 * Removes a trip
	 *
	 * @param trip The trip to be removed
	 */
	public void deleteTrip(Trip trip) {
		trips.remove(trip);
	}

	/**
	 * Recursive binary search function to return the location of the tripID.
	 *
	 * @param trips ArrayList of trips
	 * @param l int
	 * @param r int
	 * @param tripID the tripID used in searching
	 * @return int Location of the tripID
	 */
	private int binarySearch(ArrayList<Trip> trips, int l, int r, String tripID) {
		if (r >= l) {
			int mid = l + (r - l)/2;

			if (trips.get(mid).getTripID().equals(tripID)) {
				return mid;
			}

			if (trips.get(mid).getTripID().compareTo(tripID) > 0) {
				return binarySearch(trips, l, mid - 1, tripID);
			}

			return binarySearch(trips, mid+1, r, tripID);
		}

		return -1;
	}


	/**
	 * Searches for all trips corresponding to a particular routeID
	 *
	 * @param routeID the routeID used in searching for all trips corresponding to it
	 * @return tripsOnRoute all the trips on that specific route
	 */
	public List<Trip> findTripsOnRoute(String routeID) {
		List<Trip> tripsOnRoute = new ArrayList<>();
		for(Trip trip : trips) {
			if (trip.getRouteID().equals(routeID))
			{
				tripsOnRoute.add(trip);
			}
		}
		return tripsOnRoute;
	}

}