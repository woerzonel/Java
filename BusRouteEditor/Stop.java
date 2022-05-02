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


/**
 * @author Alex Shulta
 * @version 1.0
 * @created 12-Apr-2018 10:33:09 AM
 */
public class Stop implements Comparable{

	private String stopID;
	private String stopName;
	private String stopDesc;
	private String stopLat;
	private String stopLon;

	/**
	 * Constructor for Stop with all necessary data
	 *
	 * @param stopID stop_id associated with Stop
	 * @param stopName stop_name associated with Stop
	 * @param stopDesc stop_desc associated with Stop
	 * @param stopLat stop_lat associated with Stop
	 * @param stopLon stop_lon assocated with Stop
	 */
	public Stop(String stopID, String stopName, String stopDesc, String stopLat, String stopLon) {
		this.stopID = stopID;
		this.stopName = stopName;
		this.stopDesc = stopDesc;
		this.stopLat = stopLat;
		this.stopLon = stopLon;
	}


	public String getStopID() {
		return stopID;
	}

	public void setStopID(String stopID) {
		this.stopID = stopID;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopDesc() {
		return stopDesc;
	}

	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}

	public String getStopLat() {
		return stopLat;
	}

	public void setStopLat(String stopLat) {
		this.stopLat = stopLat;
	}

	public String getStopLon() {
		return stopLon;
	}

	public void setStopLon(String stopLon) {
		this.stopLon = stopLon;
	}

	/**
	 * Compares two stopIDs lexicographically
	 *
	 * @param o the object to be used in comparison
	 * @return negative, zero, or positive integer based upon the result of the comparison
	 */
	@Override
	public int compareTo(Object o) {
		return this.getStopID().compareTo(((Stop)o).getStopID());
	}

	/**
	 * To string method returning all data plus commas
	 *
	 * @return String of all data combined with commas
	 */
	@Override
	public String toString(){
		return  stopID + "," + stopName + "," + stopDesc + "," + stopLat + "," + stopLon;
	}
}