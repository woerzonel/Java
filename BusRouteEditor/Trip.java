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


/**
 * @author Nick Scharrer (scharrernf@msoe.edu)
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */
public class Trip implements Comparable {


	private String routeID;
	private String serviceID;
	private String tripID;
	private String tripHeadsign;
	private String directionID;
	private String blockID;
	private String shapeID;

    /**
     * Constructor for Trip with all data
	 *
     * @param routeID route_id associated with trip
     * @param serviceID service_id associated with trip
     * @param tripID trip_id associated with trip
     * @param tripHeadsign trip_headsign associated with trip
     * @param directionID direction_id associated with trip
     * @param blockID block_id associated with trip
     * @param shapeID shape_id associated with trip
     */
	public Trip(String routeID, String serviceID, String tripID, String tripHeadsign, String directionID, String blockID, String shapeID){
		this.routeID = routeID;
		this.serviceID = serviceID;
		this.tripID = tripID;
		this.tripHeadsign = tripHeadsign;
		this.directionID = directionID;
		this.blockID = blockID;
		this.shapeID = shapeID;
	}

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}

	public String getDirectionID() {
		return directionID;
	}

	public void setDirectionID(String directionID) {
		this.directionID = directionID;
	}

	public String getBlockID() {
		return blockID;
	}

	public void setBlockID(String blockID) {
		this.blockID = blockID;
	}

	public String getShapeID() {
		return shapeID;
	}

	public void setShapeID(String shapeID) {
		this.shapeID = shapeID;
	}

	/**
	 * Compares two tripIDs lexicographically
	 *
	 * @param o the object to be used in comparison
	 * @return negative, zero, or positive integer based upon the result of the comparison
	 */
	@Override
	public int compareTo(Object o) {
		return this.getTripID().compareTo(((Trip)o).getTripID());
	}

	/**
	 * To string method returning all data plus commas
	 *
	 * @return String of all data combined with commas
	 */
	@Override
	public String toString(){
		return routeID + "," + serviceID + "," + tripID + "," + tripHeadsign + "," +directionID + "," + blockID + "," + shapeID;
	}

}