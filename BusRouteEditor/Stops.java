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
import java.util.Iterator;

/**
 * @author Alex Shulta
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class Stops {

	private ArrayList<Stop> stops;

	/**
	 * Constructor for Stops, instantiates new ArrayList to hold all Stop objects
	 */
	public Stops(){ stops = new ArrayList<>(); }


	/**
	 * Add a Stop object with populated data to the Collection of Stops
	 * @param stop Stop object with data
	 */
	public void addStop(Stop stop) {
		stops.add(stop);
	}

	/**
	 * Removes a stop
	 *
	 * @param stop The stop to be removed
	 */
	public void deleteStop(Stop stop) {
		stops.remove(stop);
	}


	/**
	 * Sorts the collection of stops
	 */
	public void sortStops() {
		Collections.sort(stops);
	}

	/**
	 * Gets all stops
	 *
	 * @return all the stops to be returned
	 */
	public Collection<Stop> getAllStops(){
		return stops;
	}

	/**
	 * Makes a call to the binarySearch function to search for a stop with a particular stopID
	 *
	 * @param stopID The ID of the stop of type string used in searching
	 * @return The location of the stop corresponding to the stopID
	 */
	public Stop searchStop(String stopID){
		return stops.get(binarySearch(stops, 0, stops.size()-1, stopID));
	}

	/**
	 * Recursive binary search function to return the location of the stopID.
	 *
	 * @param stops ArrayList of stops
	 * @param l int
	 * @param r int
	 * @param stopID the stopID used in searching
	 * @return int Location of the stopID
	 */
	private int binarySearch(ArrayList<Stop> stops, int l, int r, String stopID) {
		if (r >= l) {
			int mid = l + (r - l)/2;

			if (stops.get(mid).getStopID().equals(stopID)) {
				return mid;
			}

			if (stops.get(mid).getStopID().compareTo(stopID) > 0) {
				return binarySearch(stops, l, mid - 1, stopID);
			}

			return binarySearch(stops, mid+1, r, stopID);
		}

		return -1;
	}

}