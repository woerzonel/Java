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
 * @created 12-Apr-2018 10:33:10 AM
 */
public class StopObserver implements Observer {

	private Collection<Stop> stops;

	/**
	 * Constructor for StopObserver, instantiates empty ArrayList to hold Stop objects
	 */
	public StopObserver(){ stops = new ArrayList<>(); }


	/**
	 * Update the Collection of Stops from the TransitSystem
	 *
	 * @param arg TransitSystem object that implements Subject
	 */
	public void update(Subject arg){ stops = ((TransitSystem)arg).getStopCollection(); }

	public Collection<Stop> getStops() {
		return stops;
	}

}