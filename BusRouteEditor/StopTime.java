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


/**
 * @author Reid M. Witt
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class StopTime {

	private String tripID;
	private String arrivalTime;
	private String departureTime;
	private String stopID;
	private String stopSequence;
	private String stopHeadsign;
	private String pickupType;
	private String dropOffType;

	/**
	 * Constructor for StopTime with the data associated with it
	 * @param tripID trip_id associated with StopTime
	 * @param arrivalTime arrival_time associated with StopTime
	 * @param departureTime departure_time associated with StopTime
	 * @param stopID stop_id associated with StopTime
	 * @param stopSequence stop_sequence associated with StopTime
	 * @param stopHeadsign stop_headsign associated with StopTime
	 * @param pickupType pickup_type associated with StopTime
	 * @param dropOffType drop_off_type associated with StopTime
	 */
	public StopTime(String tripID, String arrivalTime, String departureTime, String stopID, String stopSequence, String stopHeadsign, String pickupType, String dropOffType) {
		this.tripID = tripID;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stopID = stopID;
		this.stopSequence = stopSequence;
		this.stopHeadsign = stopHeadsign;
		this.pickupType = pickupType;
		this.dropOffType = dropOffType;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getStopID() {
		return stopID;
	}

	public void setStopID(String stopID) {
		this.stopID = stopID;
	}

	public String getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(String stopSequence) {
		this.stopSequence = stopSequence;
	}

	public String getStopHeadsign() {
		return stopHeadsign;
	}

	public void setStopHeadsign(String stopHeadsign) {
		this.stopHeadsign = stopHeadsign;
	}

	public String getPickupType() {
		return pickupType;
	}

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	public String getDropOffType() {
		return dropOffType;
	}

	public void setDropOffType(String dropOffType) {
		this.dropOffType = dropOffType;
	}

	/**
	 * To string method returning all data plus commas
	 *
	 * @return String of all data combined with commas
	 */
	@Override
	public String toString(){
		return tripID + "," + arrivalTime + "," + departureTime + "," + stopID + "," + stopSequence + "," + stopHeadsign + "," + pickupType + "," + dropOffType;
	}
}