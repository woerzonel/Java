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


import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Nick Scharrer (scharrernf@msoe.edu)
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class TripObserver implements Observer {

	private Collection<Trip> trips;

	/**
	 * Constructor for TripObserver, instantiates a new ArrayList to hold trips
	 */
	public TripObserver(){
		trips = new ArrayList<>();
	}

	/**
	 * Update the Collection of Trips
	 * @param arg TransitSystem that implements Subject
	 */
	public void update(Subject arg){
		trips = ((TransitSystem)arg).getTripCollection();
	}

	public Collection<Trip> getTrips() {
		return trips;
	}

}