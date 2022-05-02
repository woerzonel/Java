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


import java.io.*;
import java.util.ArrayList;


/**
 * @author Nick Scharrer (scharrernf@msoe.edu)
 * @version 1.0
 * @created 12-Apr-2018 10:33:09 AM
 */
public class FileHandler {

    // Declared global objects used throughout FileHandler
	private String filepath;
	private String lineSplitRegex = ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
	private ArrayList<Object> transitData;

	public FileHandler(){

	}

	public String getLineSplitRegex() {
		return lineSplitRegex;
	}
	

	/**
	 * Exports the selected file to a chosen directory
     *
	 * @param filePath
	 */
	public ArrayList<Object> exportFile(String filePath){
		return transitData;
	}


    /**
     * Validates the header of routes.txt
     *
     * @param line The string header to routes.txt
     * @return Boolean True or False regarding whether the header is valid
     */
	public boolean routesHeaderValidate(String line){
		return line.equals("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
	}

    /**
     * Validates the data of routes.txt. Ensures mandatory field are completed
     *
     * @param line The sting of data for routes.txt
     * @return Boolean True or False regarding where the data is valid
     */
	public boolean routesDataValidate(String[] line){
		if(line.length < 8){
			return false;
		}

		if(line[0].isEmpty() || line[7].isEmpty()){
			return false;
		}
		if(line[7].length() != 6){
			return false;
		}

		try {
			String colorHex[] = line[7].split("");
			int red = Integer.decode("0x"+colorHex[0] + colorHex[1]);
			int green = Integer.decode("0x"+colorHex[2] + colorHex[3]);
			int blue = Integer.decode("0x"+colorHex[4] + colorHex[5]);
		}catch(NumberFormatException ex){
			return false;
		}
		return true;
	}

	/**
	 * Alex Shulta
	 *
	 * Simple method to check if the header is the same as the properly formatted header
	 * for a stops.txt file.
     *
	 * @param line - string of the header of the stops.txt file
	 * @return boolean to whether the header is correctly formatted with all of the components
	 */
	public boolean stopsHeaderValidate(String line){
		return line.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
	}

	/**
	 * Alex Shulta
	 *
	 * Method to check the individual formatting of the data
	 * for a stops.txt file.  Runs through to check if data
	 * is there or missing, and for logical data (longitude and latitude),
	 * to make sure they are not breaching the limits.
     *
	 * @param line - string array of the data divided by commas
	 * @return boolean to whether the data is valid or not
	 */
	public boolean stopsDataValidate(String[] line){
		double latMin = -90.0;
		double latMax = 90.0;
		double lonMin = -180.0;
		double lonMax = 180.0;
		boolean validData = true;
		if(!line[0].isEmpty()) {
			if(validData) {
				if (!line[1].isEmpty()) {
					//stop desc (line[2]) is not required
					try {
						double tempLat = Double.parseDouble(line[3]);
						if (tempLat < latMin || tempLat > latMax) {
							validData = false;
						}
					} catch (NumberFormatException exception) {
						validData = false;
					}
					if (validData) {
						try {
							double tempLon = Double.parseDouble(line[4]);
							if (tempLon < lonMin || tempLon > lonMax) {
								validData = false;
							}
						} catch (NumberFormatException exception) {
							validData = false;
						}
					}
				} else {
					validData = false;
				}
			}
		} else {
			validData = false;
		}
		return validData;
	}

	/**
	 * --- Reid M. Witt
	 *
	 * Checks the formatting and logic of the header within a stoptime file
     *
	 * @param line The String header within the file
	 * @return Boolean regarding whether header is valid or not.
	 */
	public boolean stopTimesHeaderValidate(String line){
		return line.equals("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
	}

	/**
	 * --- Reid M. Witt
	 *
	 * Checks the formatting and logic of the data within a stoptime file using
	 * various helper methods. Uses requirements for this file type.
     *
	 * @param line The String arrays of data within the file
	 * @return Boolean regarding whether data is valid or not.
	 */
	public boolean stopTimesDataValidate(String[] line){

		if (checkArrivalFormat(line) == false) {
			return false;
		}
		if (checkDepartureFormat(line) == false) {
			return false;
		}
		if (arrivalBeforeDeparture(line) == false) {
			return false;
		}
		if (line[0].isEmpty() || line[3].isEmpty() || line[4].isEmpty()) {
			return false;
		}
		else {
			try {
				if (Integer.parseInt(line[4]) < 0) {
					return false;
				}
			} catch (NumberFormatException ex) {
				return false;
			}
		}
		return true;
	}

	public boolean stopTimesDataValidateGroupEdit(String[] line) {
		boolean valid = true;
		if (checkArrivalFormat(line) == false) {
			valid = false;
		}
		if (checkDepartureFormat(line) == false) {
			valid = false;
		}
		if (arrivalBeforeDeparture(line) == false) {
			valid = false;
		}
		if (line[0].isEmpty() || line[3].isEmpty()) {
			valid = false;
		}
		if (!line[4].isEmpty()) {
			try {
				if (Integer.parseInt(line[4]) < 0) {
					valid = false;
				}
			} catch (NumberFormatException ex) {
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * --- Reid M. Witt
	 *
	 * Checks that the arrival time is in correct HH:MM:SS format
     *
	 * @param line original line of data from stop_times.txt file
	 * @return boolean validData
	 */
	private boolean checkArrivalFormat(String[] line) {
		boolean validData = true;
		
		// Isolate arrival time from original string array, check its format
		String arrival_time = line[1];
		String[] splitArrivalTime = arrival_time.split(":");
		for(int i = 0; i < splitArrivalTime.length; i++) {
			try {
				if (i == 0) {
					if ((Integer.parseInt(splitArrivalTime[i]) < 0) || (Integer.parseInt(splitArrivalTime[i]) > 48)) {
						validData = false;
						break;
					}
				} else {
					if ((Integer.parseInt(splitArrivalTime[i]) < 0) || (Integer.parseInt(splitArrivalTime[i]) > 59)) {
						validData = false;
						break;
					}
				}
			} catch (NumberFormatException ex) {
				validData = false;
			}
		}
		return validData;
	}

	/**
	 * --- Reid M. Witt
	 *
	 * Checks that the departure time is in correct HH:MM:SS format
     *
	 * @param line original line of data from stop_times.txt file
	 * @return boolean validData
	 */
	private boolean checkDepartureFormat(String[] line) {

		boolean validData = true;

		// Isolate departure time from original string array, check its format
		String departure_time = line[2];
		String[] splitDepartureTime = departure_time.split(":");
		for (int i = 0; i < splitDepartureTime.length; i++) {
			try {
				if (i == 0) {
					if ((Integer.parseInt(splitDepartureTime[i]) < 0) || (Integer.parseInt(splitDepartureTime[i]) > 48)) {
						validData = false;
						break;
					}
				} else {
					if ((Integer.parseInt(splitDepartureTime[i]) < 0) || (Integer.parseInt(splitDepartureTime[i]) > 59)) {
						validData = false;
						break;
					}
				}
			} catch (NumberFormatException nfe) {
				validData = false;
			}
		}
		return validData;
	}


	/**
	 * --- Reid M. Witt
	 *
	 * Checks that the arrival time does indeed occur before the departure time (or same time)
     *
	 * @param line original line of data from stop_times.txt file
	 * @return boolean validData
	 */
	private boolean arrivalBeforeDeparture (String[] line) {

		boolean validData = true;

		String arrival_time = line[1];
		String departure_time = line[2];
		String[] splitArrivalTime = arrival_time.split(":");
		String[] splitDepartureTime = departure_time.split(":");
		for (int i = 0; i < 3; i++) {
			try {
				if (Integer.parseInt(splitArrivalTime[i]) > Integer.parseInt(splitDepartureTime[i])) {
					validData = false;
					break;
				}
			} catch (NumberFormatException nfe) {
			}
		}

		return validData;
	}

    /**
     * Validates the header of trips.txt
     *
     * @param line The string header to trips.txt
     * @return Boolean True or False regarding whether the header is valid
     */
	public boolean tripsHeaderValidate(String line){
		return line.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
	}

    /**
     * Validates the data of routes.txt. Ensures mandatory field are completed
     *
     * @param line The sting of data for routes.txt
     * @return Boolean True or False regarding where the data is valid
     */
	public boolean tripsDataValidate(String[] line){
		boolean valid = false;
		if(line.length == 7) {
			// route_id and trip_id required but do not have specified format
			if ((!line[0].isEmpty()) && (!line[2].isEmpty())) {
				// direction_id must be a 0 or a 1 according ot gtfs reference guide (but is not required)
				if (line[4].isEmpty()) {
					valid = true;
				}
				else {
					try {
						if (((Integer.parseInt(line[4]) == 0) || (Integer.parseInt(line[4]) == 1))) {
							valid = true;
						}
					} catch (NumberFormatException ex) {
						valid = false;
					}
				}
			}
		}
		return valid;
	}

	/**
	 * TODO
	 */
	private void exportRoutes(){

	}

	/**
	 * TODO
	 */
	private void exportStops(){

	}

	/**
	 * TODO
	 */
	private void exportStopTimes(){

	}

	/**
	 * TODO
	 */
	private void exportTrips(){

	}

    /**
     * Ensures the directory chosen from import contains a folder that contains all
     * 4 files within a GTFS file. Throws an exception if not true.
     *
     * @param routesFile Routes file within the GTFS file.
     * @param stopsFile Stops file within the GTFS file.
     * @param stopTimesFile Stoptimes file withing the GTFS file.
     * @param tripsFile Trips file within the GTFS file.
     * @return Boolean True if the all files exists.
     * @throws FileNotFoundException The folder does not contain all necessary files.
     */
	private boolean validateGTFSDirectory(File routesFile, File stopsFile, File stopTimesFile, File tripsFile) throws FileNotFoundException {
		if (routesFile.exists() && stopsFile.exists() && stopTimesFile.exists() && tripsFile.exists()) {
			return true;
		} else {
			throw new FileNotFoundException("One or more of necessary GTFS files was not found in directory.");
		}
	}

    /**
     * Imports routes.txt from the GTFS file
     *
     * @param routesFile routes.txt
     * @return routes Routes object with data
     */
	private Routes importRoutes(File routesFile) {
		Routes routes = new Routes();
		try {
			BufferedReader br = new BufferedReader(new FileReader(routesFile));
			if (!routesHeaderValidate(br.readLine())) {
				throw new InvalidHeaderLineException(0);
			}

			String line;
			while (((line = br.readLine()) != null)) {
				if(line.endsWith(",")) {
					line += '\0';
				}
				// split each line on the commas
				String[] lineData = line.split(lineSplitRegex);

				try {
					// Validate each line for each file
					if (!routesDataValidate(lineData)) {
						throw new InvalidContentLineException(0);
					}
				} catch (InvalidContentLineException ex) {
					Controller.alertDataInvalid(ex.getI());
					// skip line if invalid
					continue;
				}

				routes.addRoute(new Route(lineData[0].replaceAll("\\s+",""), lineData[1].replaceAll("\\s+",""), lineData[2], lineData[3], lineData[4], lineData[5], lineData[6], lineData[7], lineData[8]));
			}
		} catch (InvalidHeaderLineException ex) {
			Controller.alertHeaderInvalid(ex.getI());
		}
		catch (IOException ex) {
			Controller.alertIOProblem();
		}
		routes.sortRoutes();
		return routes;
	}

    /**
     * Imports trips.txt from the GTFS file
     *
     * @param tripsFile File object itself
     * @param tripsOnRoute TripsOnRoute object for searching
     * @param routes Routes within the files
     * @return returnObjects Trips and tripsOnRoute from data
     */
	private ArrayList<Object> importTrips(File tripsFile, TripsOnRoute tripsOnRoute, Routes routes) {
		Trips trips = new Trips();
		try {
			BufferedReader br = new BufferedReader(new FileReader(tripsFile));
			if (!tripsHeaderValidate(br.readLine())) {
				throw new InvalidHeaderLineException(1);
			}

			String line;
			while (((line = br.readLine()) != null)) {
				if(line.endsWith(",")) {
					line += '\0';
				}
				// split each line on the commas
				String[] lineData = line.split(lineSplitRegex);

				try {
					// Validate each line for each file
					if (!tripsDataValidate(lineData)) {
						throw new InvalidContentLineException(0);
					}
				} catch (InvalidContentLineException ex) {
					Controller.alertDataInvalid(ex.getI());
					// skip line if invalid
					continue;
				}
				Trip trip = new Trip(lineData[0].replaceAll("\\s+",""), lineData[1].replaceAll("\\s+",""), lineData[2].replaceAll("\\s+",""), lineData[3], lineData[4].replaceAll("\\s+",""), lineData[5].replaceAll("\\s+",""), lineData[5].replaceAll("\\s+",""));
				trips.addTrip(trip);
				tripsOnRoute.addTripOnRoute(trip.getRouteID(), trip);
				Route route = routes.searchRoute(trip.getRouteID());
				tripsOnRoute.addRouteOnTrip(trip.getTripID(), route);
			}
		} catch (InvalidHeaderLineException ex) {
			Controller.alertHeaderInvalid(ex.getI());
		} catch (IOException ex) {
			Controller.alertIOProblem();
		}
		trips.sortTrips();
		ArrayList<Object> returnObjects = new ArrayList<>(2);
		returnObjects.add(0, trips);
		returnObjects.add(1, tripsOnRoute);
		return returnObjects;
	}

    /**
     * Imports stops.txt from the GTFS file
     *
     * @param stopsFile stops.txt
     * @return routes Stops object with data
     */
	private Stops importStops(File stopsFile) {
		Stops stops = new Stops();
		try {
			BufferedReader br = new BufferedReader(new FileReader(stopsFile));
			if (!stopsHeaderValidate(br.readLine())) {
				throw new InvalidHeaderLineException(2);
			}

			String line;
			while (((line = br.readLine()) != null)) {
				if(line.endsWith(",")) {
					line += '\0';
				}
				// split each line on the commas
				String[] lineData = line.split(lineSplitRegex);

				try {
					// Validate each line for each file
					if (!stopsDataValidate(lineData)) {
						throw new InvalidContentLineException(2);
					}
				} catch (InvalidContentLineException ex) {
					Controller.alertDataInvalid(ex.getI());
					// skip line if invalid
					continue;
				}

				stops.addStop(new Stop(lineData[0].replaceAll("\\s+",""), lineData[1], lineData[2], lineData[3], lineData[4]));
			}
		} catch (InvalidHeaderLineException ex) {
			Controller.alertHeaderInvalid(ex.getI());
		}
		catch (IOException ex) {
			Controller.alertIOProblem();
		}
		stops.sortStops();
		return stops;
	}

    /**
     * Imports Stoptimes.txt from the GTFS file
     *
     * @param stopTimesFile File object itself
     * @param stopsOnTrip StopsOnTrip object for searching
     * @param trips Trips within the files
     * @param stops Stops within the files
     * @return returnObjects Stoptimes and stopsOnTrip from data
     */
	private ArrayList<Object> importStopTimes(File stopTimesFile, StopsOnTrip stopsOnTrip, Trips trips, Stops stops) {
		StopTimes stopTimes = new StopTimes();
		try {
			BufferedReader br = new BufferedReader(new FileReader(stopTimesFile));
			if (!stopTimesHeaderValidate(br.readLine())) {
				throw new InvalidHeaderLineException(3);
			}

			String line;
			while (((line = br.readLine()) != null)) {
				if(line.endsWith(",")) {
					line += '\0';
				}
				// split each line on the commas
				String[] lineData = line.split(lineSplitRegex);

				try {
					// Validate each line for each file
					if (!stopTimesDataValidate(lineData)) {
						throw new InvalidContentLineException(3);
					}
				} catch (InvalidContentLineException ex) {
					Controller.alertDataInvalid(ex.getI());
					// skip line if invalid
					continue;
				}
				StopTime stopTime = new StopTime(lineData[0].replaceAll("\\s+",""), lineData[1], lineData[2], lineData[3].replaceAll("\\s+",""), lineData[4], lineData[5], lineData[6], lineData[7]);
				stopTimes.addStopTime(stopTime);

				Stop stop = stops.searchStop(stopTime.getStopID());
				Trip trip = trips.searchTrip(stopTime.getTripID());

				stopsOnTrip.addStopOnTrip(stopTime.getTripID(), stop);
				stopsOnTrip.addStopTimeOnTrip(stopTime.getTripID(), stopTime);
				stopsOnTrip.addTripOnStop(stopTime.getStopID(), trip);

			}
		} catch (InvalidHeaderLineException ex) {
			Controller.alertHeaderInvalid(ex.getI());
		} catch (IOException ex) {
			Controller.alertIOProblem();
		}
		ArrayList<Object> returnObjects = new ArrayList<>(2);
		returnObjects.add(0, stopTimes);
		returnObjects.add(1, stopsOnTrip);
		return returnObjects;
	}

    /**
     * Gets called from controller to handle importing. Uses various helper methods for validation and
     * importing all files within the GTFS folder.
     *
     * @param filePath String filepath of the selected file/folder
     * @return transitData The transit data of the files
     * @throws FileNotFoundException The selected file directory is not a valid GTFS. Throws
     * to create alert dialogue in controller.
     */
	public ArrayList<Object> importFile(String filePath) throws FileNotFoundException {
		File routesFile = new File(filePath + "/routes.txt");
		File stopsFile = new File(filePath + "/stops.txt");
		File stopTimesFile = new File(filePath + "/stop_times.txt");
		File tripsFile = new File(filePath + "/trips.txt");

		Routes routes;
		Stops stops;
		StopTimes stopTimes;
		Trips trips;
		TripsOnRoute tripsOnRoute = new TripsOnRoute();
		StopsOnTrip stopsOnTrip = new StopsOnTrip();


		transitData = new ArrayList<>(6);

		// Check if directory contains the correct GTFS files
		validateGTFSDirectory(routesFile, stopsFile, stopTimesFile, tripsFile);


		routes = importRoutes(routesFile);
		ArrayList<Object> tripsData = importTrips(tripsFile, tripsOnRoute, routes);
		trips = (Trips)tripsData.get(0);
		tripsOnRoute = (TripsOnRoute)tripsData.get(1);
		stops = importStops(stopsFile);

		ArrayList<Object> stopTimesData = importStopTimes(stopTimesFile, stopsOnTrip, trips, stops);
		stopTimes = (StopTimes)stopTimesData.get(0);
		stopsOnTrip = (StopsOnTrip)stopTimesData.get(1);

		transitData.add(0, routes);
		transitData.add(1, stops);
		transitData.add(2, stopTimes);
		transitData.add(3, trips);
		transitData.add(4, tripsOnRoute);
		transitData.add(5, stopsOnTrip);
		return transitData;
	}

}