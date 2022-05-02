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


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.List;

/**
 * @author Alex Shulta
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class StopsOnTrip {
    private ListMultimap<String, Trip> tripsOnStop;
    private ListMultimap<String, Stop> stopsOnTrip;
    private ListMultimap<String, StopTime> stopTimesOnTrip;

	/**
	 * Creates list of tripsOnStop, stopsOnTrip, stopTimesOnTrip
	 */
	public StopsOnTrip(){
        tripsOnStop = ArrayListMultimap.create();
        stopsOnTrip = ArrayListMultimap.create();
        stopTimesOnTrip = ArrayListMultimap.create();
	}

    /**
     * Adds a trip with a corresponding stopID (relation) if not already existing
     *
     * @param stopID String stopID of the requested stop
     * @param trip Trip object
     */
	public void addTripOnStop(String stopID, Trip trip) {
	    if (!tripsOnStop.containsEntry(stopID, trip)) {
            tripsOnStop.put(stopID, trip);
        }
    }

    /**
     * Adds a stop with a corresponding tripID (relation) if not already existing
     *
     * @param tripID String tripID of the requested trip
     * @param stop Stop object
     */
    public void addStopOnTrip(String tripID, Stop stop) {
	    if (!stopsOnTrip.containsEntry(tripID, stop)) {
	        stopsOnTrip.put(tripID, stop);
        }
    }

    /**
     * Adds stoptime with a corresponding trip (relation) if not already existing
     *
     * @param tripID String tripID of the requested trip
     * @param stopTime Stoptime object
     */
    public void addStopTimeOnTrip(String tripID, StopTime stopTime) {
	    if (!stopTimesOnTrip.containsEntry(tripID, stopTime)) {
	        stopTimesOnTrip.put(tripID, stopTime);
        }
    }

    /**
     * Returns tripsOnStop object with corresponding stopID
     *
     * @param stopID Requested stopID
     * @return tripsOnStop with passed in stopID
     */
    public List<Trip> searchForTripsOnStop(String stopID) {
        return tripsOnStop.get(stopID);
    }

    /**
     * Returns stopsOnTrip object with corresponding tripID
     *
     * @param tripID Requested tripID
     * @return stopsOnTrip with passed in tripID
     */
    public List<Stop> searchForStopsOnTrip(String tripID) {
	    return stopsOnTrip.get(tripID);
    }

    /**
     * Returns stopTimesOnTrip object with corresponding tripID
     *
     * @param tripID Requested tripID
     * @return stopTimesOnTrip with passed in tipID
     */
    public List<StopTime> searchForStopTimesOnTrip(String tripID) {
	    return stopTimesOnTrip.get(tripID);
    }


    /**
     * Deletes a  specific trip that corresponds to a particular stopID
     *
     * @param stopID the stopID which contains the trip(s) to be deleted
     * @param trip the trip to be deleted
     */
    public void deleteTripOnStop(String stopID, Trip trip) {
        if (tripsOnStop.containsEntry(stopID, trip)) {
            tripsOnStop.remove(stopID, trip);
        }
    }

    /**
     * Deletes a  specific stop that corresponds to a particular tripID
     *
     * @param tripID the tripID which contains the stop(s) to be deleted
     * @param stop the stop to be deleted
     */
    public void deleteStopOnTrip(String tripID, Stop stop) {
        if (stopsOnTrip.containsEntry(tripID, stop)) {
            stopsOnTrip.remove(tripID, stop);
        }
    }

    /**
     * Deletes a  specific stopTime that corresponds to a particular tripID
     *
     * @param tripID the tripID which contains the stopTime(s) to be deleted
     * @param stopTime the stopTime to be deleted
     */
    public void deleteStopTimeOnTrip(String tripID, StopTime stopTime) {
        if (stopTimesOnTrip.containsEntry(tripID, stopTime)) {
            stopTimesOnTrip.remove(tripID, stopTime);
        }
    }

}