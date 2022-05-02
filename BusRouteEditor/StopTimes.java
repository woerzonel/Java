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
import java.util.List;

/**
 * @author Reid M. Witt
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class StopTimes {

	private Collection<StopTime> stopTimes;

    /**
     * Constructor for StopTimes, instantiate empty stopTimes collection to an ArrayList
     */
	public StopTimes(){
		stopTimes = new ArrayList<>();
	}


	/**
	 * Add a StopTime to the Collection
	 * @param stopTime StopTime object with data populated
	 */
	public void addStopTime(StopTime stopTime){
		stopTimes.add(stopTime);
	}

	/**
	 * Gets all topTimes
	 *
	 * @return all stopTimes to be returned
	 */
	public Collection<StopTime> getAllStopTimes(){
		return stopTimes;
	}


	/**
	 * Removes a stopTime
	 * @param stopTime the stopTime to be removed
	 */
	public void deleteStopTime(StopTime stopTime) {
		stopTimes.remove(stopTime);
	}

	/**
	 * Searches for a particular tripID via an entered stopID
	 *
	 * @param stopID the stopID used in searching
	 * @return the list of tripIDs containing the passed in stopID
	 */
	public List<String> searchTripIDsByStopID(String stopID) {
		List<String> tripIDList = new ArrayList<>();
		String currentID;
		for (StopTime stopTime : stopTimes) {
			if (stopTime.getStopID().equals(stopID)) {
				currentID = stopTime.getTripID();
				if (!tripIDList.contains(currentID)) {
					tripIDList.add(currentID);
				}
			}
		}
		return tripIDList;
	}


	/**
	 * Searches for stopTimes via an entered tripID
	 *
	 * @param tripID the tripID used in searching
	 * @return the list of StopTimes associated with the passed in tripID
	 */
	public List<StopTime> findStopTimesOnTrip(String tripID) {
		List<StopTime> stopTimesOnTrip = new ArrayList<>();
		for(StopTime stopTime : stopTimes) {
			if (stopTime.getTripID().equals(tripID)) {
				stopTimesOnTrip.add(stopTime);
			}
		}
		return stopTimesOnTrip;
	}

	public StopTime findStopTimesOnTripWithStopID(String tripID, String stopID) {

		for(StopTime stopTime : stopTimes) {
			if (stopTime.getTripID().equals(tripID) && stopTime.getStopID().equals(stopID)) {
				return stopTime;
			}
		}
		return null;
	}

}