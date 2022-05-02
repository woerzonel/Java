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
public class Route implements Comparable{

	private String routeID;
	private String agencyID;
	private String routeShortName;
	private String routeLongName;
	private String routeDesc;
	private String routeType;
	private String routeURL;
	private String routeColor;
	private String routeTextColor;

	/**
	 * Constructor for Route with all data necessary
	 *
	 * @param routeID route_id associated with route
	 * @param agencyID agency_id associated with route
	 * @param routeShortName route_short_name associated with route
	 * @param routeLongName route_long_name associated with route
	 * @param routeDesc route_desc associated with route
	 * @param routeType route_type associated with route
	 * @param routeURL route_URL associated with route
	 * @param routeColor route_color associated with route
	 * @param routeTextColor route_text_color associated with route
	 */
	public Route(String routeID, String agencyID, String routeShortName, String routeLongName, String routeDesc, String routeType, String routeURL, String routeColor, String routeTextColor) {
		this.routeID = routeID;
		this.agencyID = agencyID;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeDesc = routeDesc;
		this.routeType = routeType;
		this.routeURL = routeURL;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;
	}

	/**
	 * Compares two routeIDs lexicographically
	 *
	 * @param o the object to be used in comparison
	 * @return negative, zero, or positive integer based upon the result of the comparison
	 */
	@Override
	public int compareTo(Object o) {
		return this.getRouteID().compareTo(((Route)o).getRouteID());
	}

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}

	public String getAgencyID() {
		return agencyID;
	}

	public void setAgencyID(String agencyID) {
		this.agencyID = agencyID;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	public String getRouteDesc() {
		return routeDesc;
	}

	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getRouteURL() {
		return routeURL;
	}

	public void setRouteURL(String routeURL) {
		this.routeURL = routeURL;
	}

	public String getRouteColor() {
		return routeColor;
	}

	public void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}

	public String getRouteTextColor() {
		return routeTextColor;
	}

	public void setRouteTextColor(String routeTextColor) {
		this.routeTextColor = routeTextColor;
	}

	/**
	 * To string method returning all data plus commas
	 *
	 * @return String of all data combined with commas
	 */
	@Override
	public String toString(){
		return routeID + "," + agencyID + "," + routeShortName + "," +  routeLongName + "," + routeDesc + "," + routeType + "," + routeURL + "," +  routeColor + "," + routeTextColor;
	}
}
