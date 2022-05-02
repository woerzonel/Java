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
 * Name: Reid Witt
 * Created: 4/12/2018
 */
package teamEtransitsystem;


import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Reid M. Witt
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class StopTimeObserver implements Observer {

	private Collection<StopTime> stopTimes;

	/**
	 * Constructor for StopTimeObserver, instantiates empty ArrayList to hold StopTime objects
	 */
	public StopTimeObserver(){
		stopTimes = new ArrayList<>();
	}

	/**
	 * Update collection of StopTimes from the Subject
	 * @param arg TransitSystem object that implements Subject
	 */
	public void update(Subject arg){
		stopTimes = ((TransitSystem)arg).getStopTimeCollection();
	}

	public Collection<StopTime> getStopTimes() {
		return stopTimes;
	}

}