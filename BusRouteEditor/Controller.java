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
 * Name: Gabe Woerishofer
 * Created: 4/12/2018
 */
package teamEtransitsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Gabe Woerishofer
 * @version 1.0
 * @created 12-Apr-2018 10:33:09 AM
 */
public class Controller implements Initializable {

	public ListView<String> tripIDList;
	public Label chooseTripLabel;
	public Menu fileMenu;
	public HBox editStopTimeGroupButtons;
	// Declared  global objects used throughout the controller
	private FileHandler handler;
	private RouteObserver routeObserver;
	private StopObserver stopObserver;
	private StopsOnTrip stopsOnTrip;
	private StopTimeObserver stopTimeObserver;
	private TransitSystem transitSystem;
	private TripObserver tripObserver;
	private TripsOnRoute tripsOnRoute;

	private String currentTripID;

	private int whichEdit = 0;
	private ArrayList<Integer> editRoute = new ArrayList<>();
	private ArrayList<Integer> editStop = new ArrayList<>();
	private ArrayList<Integer> editStopTime = new ArrayList<>();
	private ArrayList<Integer> editTrip = new ArrayList<>();

	// FXML properties of the GUI
	@FXML private ListView<String> stopsTextArea;
	@FXML private ListView<String> stopTimesTextArea;
	@FXML private ListView<String> routesTextArea;
	@FXML private ListView<String> tripsTextArea;
	@FXML public TextField searchBarText;
	@FXML private ListView<String> searchDisplay;
	@FXML private ListView<String> searchDisplay1;
	@FXML private Label searchedForLabel;
	@FXML private Menu searchMenu;
	@FXML private Menu editMenu;
	@FXML private Menu homeMenu;
	@FXML private MenuItem exportMenuItem;
	@FXML private HBox idBox;
	@FXML private Label searchValueLabel;
	@FXML private HBox editHBox;
	@FXML private Label searchedLabel;
	@FXML private Label upcomingTripLabel;
	@FXML private Button saveChangesButton;
	@FXML private Button discardChangesButton;
	@FXML private ListView<String> editListOne;
	@FXML private ListView<String> editListTwo;
	@FXML private ListView<String> editListThree;
	@FXML private ListView<String> editListFour;
	@FXML private ListView<String> editListFive;
	@FXML private ListView<String> editListSix;
	@FXML private ListView<String> editListSeven;
	@FXML private ListView<String> editListEight;
	@FXML private ListView<String> editListNine;
	@FXML private MenuItem editStopTimeMenuItem;
	@FXML private MenuItem editStopMenuItem;
	@FXML private MenuItem editRouteMenuItem;
	@FXML private MenuItem editTripMenuItem;
	@FXML private MenuItem editStopTimesByGroupMenuItem;
	@FXML private Button addTimeBtn;
	@FXML private Button subTimeBtn;
	@FXML private Button newTimeBtn;


	/**
	 * Constructor for Controller object.
	 */
	public Controller() {
		// create the observers, attach them to the subject (TransitSystem) and notify them all to update
		routeObserver = new RouteObserver();
		stopObserver = new StopObserver();
		stopTimeObserver = new StopTimeObserver();
		tripObserver = new TripObserver();
		handler = new FileHandler();
		stopsOnTrip = new StopsOnTrip();
		tripsOnRoute = new TripsOnRoute();

	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		tripIDList.setVisible(false);
		chooseTripLabel.setVisible(false);
		editStopTimeGroupButtons.setVisible(false);
	}

	/**
	 * Handles pressing the Import Button, lets the user choose a file and uses files in that path to populate GTFS data
	 */
	@FXML
	public void importFile(){
		//A directory would be chooses and the files in that directory would be pulled. Only the 4 needed files could be in the directory.
		DirectoryChooser chooser = new DirectoryChooser();
		File file = chooser.showDialog(null);
		try {
			handler = new FileHandler();
			// use FileHandler to populate GTFS data and give back collection objects
			ArrayList<Object> populatedClasses = handler.importFile(file.getPath());

			// populate the TransitSystem with the imported data
			transitSystem = new TransitSystem((Routes)populatedClasses.get(0), (Stops)populatedClasses.get(1), (StopTimes)populatedClasses.get(2), (Trips)populatedClasses.get(3));
			
			//Adds the observer objects to this class. They handle updateing the thier respective lists. 
			transitSystem.attach(routeObserver);
			transitSystem.attach(stopObserver);
			transitSystem.attach(stopTimeObserver);
			transitSystem.attach(tripObserver);

			tripsOnRoute = (TripsOnRoute)populatedClasses.get(4);
			stopsOnTrip = (StopsOnTrip)populatedClasses.get(5);

            updateMainScreen();
		} catch (NullPointerException ex) {
			System.out.println("No file chosen.");
		} catch (FileNotFoundException fnfe) {
			alertFileInvalid();
		}
	}

	/**
	 * Updates the main window of all data with edited data from previously made edits.
	 * When an edit is made its saved to the observer lists. It needs to be repulled and displayed after changes.
	 */
	public void updateMainScreen() {
        ArrayList<Route> routeList = (ArrayList<Route>) routeObserver.getRoutes();
        ArrayList<Stop> stopList = (ArrayList<Stop>) stopObserver.getStops();
        ArrayList<StopTime> stopTimeList = (ArrayList<StopTime>) stopTimeObserver.getStopTimes();
        ArrayList<Trip> tripList = (ArrayList<Trip>) tripObserver.getTrips();

        ObservableList routeIds = FXCollections.observableArrayList();
        ObservableList stopIds = FXCollections.observableArrayList();
        ObservableList stopTimeIds = FXCollections.observableArrayList();
        ObservableList tripIds = FXCollections.observableArrayList();


        // iterate through each list of data and output the data to the fields on the GUI
        routeIds.add("Route Ids");
        for(int i = 0; i < routeList.size(); i++){
            routeIds.add(routeList.get(i).getRouteID());
        }
        routesTextArea.setItems(routeIds);

        stopIds.add("Stop Ids");
        for(int i = 0; i < stopList.size(); i++){
            stopIds.add(stopList.get(i).getStopID());
        }
        stopsTextArea.setItems(stopIds);

        stopTimeIds.add("StopTime ids");
        for(int i = 0; i < stopTimeList.size(); i++){
            stopTimeIds.add(stopTimeList.get(i).getStopID());
        }
        stopTimesTextArea.setItems(stopTimeIds);

        tripIds.add("Trip Ids");
        for(int i = 0; i < tripList.size(); i++){
            tripIds.add(tripList.get(i).getTripID());
        }
        tripsTextArea.setItems(tripIds);

		//Enables the ability to edit the displayed information
        searchBarText.setDisable(false);
        searchMenu.setDisable(false);
        editMenu.setDisable(false);
        exportMenuItem.setDisable(false);
    }

	/**
	 * Handles the Export Button, gives user the ability to export GTFS files to any directory, most notably after
	 * edits to data within the file.
	 */
	@FXML
	public void exportFile(){
		//Using directory chooser to find a location to ave
		DirectoryChooser dirchooser = new DirectoryChooser();
		File file = dirchooser.showDialog(null);
		try {
			//Attach the observers
			transitSystem.attach(routeObserver);
			transitSystem.attach(stopObserver);
			transitSystem.attach(stopTimeObserver);
			transitSystem.attach(tripObserver);

			//Save a copy of the arrays	
			ArrayList<Route> routeList = (ArrayList<Route>) routeObserver.getRoutes();
			ArrayList<Stop> stopList = (ArrayList<Stop>) stopObserver.getStops();
			ArrayList<StopTime> stopTimeList = (ArrayList<StopTime>) stopTimeObserver.getStopTimes();
			ArrayList<Trip> tripList = (ArrayList<Trip>) tripObserver.getTrips();

			//Set up where each file will be printed
			PrintWriter routeWriter = new PrintWriter(file.getPath() + "/routes.txt");
			PrintWriter stopWriter = new PrintWriter(file.getPath() + "/stops.txt");
			PrintWriter stopTimeWriter = new PrintWriter(file.getPath() + "/stop_times.txt");
			PrintWriter tripsWriter = new PrintWriter(file.getPath() + "/trips.txt");
			routeWriter.println("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");

			//Due to users being able to edit the text. It has to be split apart for editing. Then resassembled to be saved.
			//A loop that iterates through make the routes txt document line by line.
			for (int i = 0; i < routeList.size(); i++){
				routeWriter.println(routeList.get(i).getRouteID() + "," + routeList.get(i).getAgencyID()+ "," + routeList.get(i).getRouteShortName()+","+routeList.get(i).getRouteLongName()+","+routeList.get(i).getRouteDesc()+","+routeList.get(i).getRouteType()+","+routeList.get(i).getRouteURL()+","+routeList.get(i).getRouteColor()+","+routeList.get(i).getRouteTextColor());
			}

			//A loop that iterates through make the stop txt document line by line.
			stopWriter.println("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
			for(int i = 0; i < stopList.size(); i++){
				stopWriter.println(stopList.get(i).getStopID()+","+stopList.get(i).getStopName()+","+stopList.get(i).getStopDesc()+","+stopList.get(i).getStopLat()+","+stopList.get(i).getStopLon());
			}

			//A loop that iterates through make the stop_time txt document line by line.
			stopTimeWriter.println("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
			for(int i = 0; i < stopTimeList.size(); i++){
				stopTimeWriter.println(stopTimeList.get(i).getTripID()+","+stopTimeList.get(i).getArrivalTime()+","+stopTimeList.get(i).getDepartureTime()+","+stopTimeList.get(i).getStopID()+","+stopTimeList.get(i).getStopSequence()+","+stopTimeList.get(i).getStopHeadsign()+","+stopTimeList.get(i).getPickupType()+","+stopTimeList.get(i).getDropOffType());
			}

			//A loop that iterates through make the trips txt document line by line.
			tripsWriter.println("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
			for(int i = 0; i < tripList.size(); i++){
				tripsWriter.println(tripList.get(i).getRouteID()+","+tripList.get(i).getServiceID()+","+tripList.get(i).getTripID()+","+tripList.get(i).getTripHeadsign()+","+tripList.get(i).getDirectionID()+","+tripList.get(i).getBlockID()+","+tripList.get(i).getShapeID());
			}
			//Close or terminate the writers
			routeWriter.close();
			stopWriter.close();
			stopTimeWriter.close();
			tripsWriter.close();
		}catch (NullPointerException ex) {
			System.out.println("No file chosen.");
		} catch (IOException ex){
			alertIOProblem();
		}

	}

	/**
	 * Invalid Header to the file selected. Creates error dialogue
	 *
	 * @param fileType int File type selected
	 */
	public static void alertHeaderInvalid(int fileType) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Invalid File Format");
		switch (fileType) {
			case 0:
				alert.setContentText("Invalid header line: routes.txt\nFile skipped.");
				break;
			case 1:
				alert.setContentText("Invalid header line: stops.txt\nFile skipped.");
				break;
			case 2:
				alert.setContentText("Invalid header line: stopTimes.txt\nFile skipped.");
				break;
			case 3:
				alert.setContentText("Invalid header line: trips.txt\nFile skipped.");
				break;
			default:
				System.out.println("Should not be here!");
		}
		alert.showAndWait();
	}

	/**
	 * Invalid Data section of the file selected. Creates error dialogue
	 *
	 * @param fileType int File type selected
	 */
	public static void alertDataInvalid(int fileType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Data Invalid");
        switch (fileType) {
            case 0:
                alert.setContentText("Invalid content line: routes.txt\nLine skipped.");
                break;
            case 1:
                alert.setContentText("Invalid content line: stops.txt\nLine skipped.");
                break;
            case 2:
                alert.setContentText("Invalid content line: stopTimes.txt\nLine skipped.");
                break;
            case 3:
                alert.setContentText("Invalid content line: trips.txt\nLine skipped.");
                break;
            default:
                System.out.println("Should not be here!");
        }
        alert.showAndWait();
    }

	/**
	 * Invalid directory chosen from file explorer. Creates error dialogue
	 */
    public static void alertFileInvalid() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("File Invalid");
		alert.setContentText("Invalid GTFS filepath chosen.\nDoes not contain correct files.");
		alert.showAndWait();
	}

	/**
	 * ID was not provided for the search. Creates error dialogue
	 */
	public static void alertNoID() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("No ID Given");
		alert.setContentText("ID must be given to search for.");
		alert.showAndWait();
	}

	/**
	 * ID provided has no results. Might be invalid ID. Creates error dialogue
	 */
	public static void alertInvalidID() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid ID");
        alert.setContentText("Nothing found for given ID.\nID may be invalid.");
        alert.showAndWait();
    }

	/**
	 * IO exception has been caught. Creates error dialogue
	 */
	public static void alertIOProblem() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("IO Exception");
		alert.setContentText("Problem occurred with input/output.\nPlease try again.");
		alert.showAndWait();
	}

    /**
     * Edit made by user has invalid data. Creates error dialogue box.
     */
	public static void alertInvalidEdit() {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Invalid Edit");
	    alert.setContentText("Data entered is not valid GTFS data.\nPlease make changes and try again.");
	    alert.showAndWait();
    }

	/**
	 * StopTime data made by user has invalid data. Creates error dialogue box.
	 */
	public static void alertInvalidStopTimeData() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Invalid StopTime Data");
		alert.setContentText("Data entered is not valid StopTime data.\nPlease make changes and try again.");
		alert.showAndWait();
	}

	/**
	 * Sets the GUI for edditing data within routes.txt
	 */
	private void setEditBox(){
		upcomingTripLabel.setVisible(false);
		saveChangesButton.setVisible(true);
		discardChangesButton.setVisible(true);
		editHBox.setVisible(true);
		idBox.setVisible(false);
		searchDisplay.setVisible((false));
		searchBarText.setVisible(false);
		searchedForLabel.setVisible(false);
		searchedLabel.setVisible(false);
		searchValueLabel.setVisible(false);
		searchMenu.setDisable(true);
		editListSix.setVisible(true);
		editListSeven.setVisible(true);
		editListEight.setVisible(true);
		editListNine.setVisible(true);
		setSyncScroll();
		homeMenu.setDisable(false);
	}

	/**
	 * Method to synchronize the scrolling for the 9
	 * list views when editing a file. Extended from 2 scrolls
	 * synced to 9.
	 *
	 * Logical basis for syncing 2 scrolls on:
	 * https://stackoverflow.com/questions/31372341/how-can-i-make-two-listviews-scroll-together-in-javafx
	 * Posted By: ItachiUchiha
	 */
	private void setSyncScroll(){
		Node n1 = editListOne.lookup(".scroll-bar");
		if(n1 instanceof ScrollBar){
			final ScrollBar bar1 = (ScrollBar) n1;
			Node n2 = editListTwo.lookup(".scroll-bar");
			if(n2 instanceof ScrollBar){
				final ScrollBar bar2 = (ScrollBar) n2;
				bar1.valueProperty().bindBidirectional(bar2.valueProperty());
				Node n3 = editListThree.lookup(".scroll-bar");
				if(n3 instanceof  ScrollBar){
					final ScrollBar bar3 = (ScrollBar) n3;
					bar1.valueProperty().bindBidirectional(bar3.valueProperty());
					Node n4 = editListFour.lookup(".scroll-bar");
					if(n4 instanceof  ScrollBar){
						final ScrollBar bar4 = (ScrollBar) n4;
						bar1.valueProperty().bindBidirectional(bar4.valueProperty());
						Node n5 = editListFive.lookup(".scroll-bar");
						if(n5 instanceof  ScrollBar){
							final ScrollBar bar5 = (ScrollBar) n5;
							bar1.valueProperty().bindBidirectional(bar5.valueProperty());
							Node n6 = editListSix.lookup(".scroll-bar");
							if(n6 instanceof  ScrollBar){
								final ScrollBar bar6 = (ScrollBar) n6;
								bar1.valueProperty().bindBidirectional(bar6.valueProperty());
								Node n7 = editListSeven.lookup(".scroll-bar");
								if(n7 instanceof  ScrollBar){
									final ScrollBar bar7 = (ScrollBar) n7;
									bar1.valueProperty().bindBidirectional(bar7.valueProperty());
									Node n8 = editListEight.lookup(".scroll-bar");
									if(n8 instanceof  ScrollBar){
										final ScrollBar bar8 = (ScrollBar) n8;
										bar1.valueProperty().bindBidirectional(bar8.valueProperty());
										Node n9 = editListNine.lookup(".scroll-bar");
										if(n9 instanceof  ScrollBar){
											final ScrollBar bar9 = (ScrollBar) n9;
											bar1.valueProperty().bindBidirectional(bar9.valueProperty());
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}


	/**
	 * Method to synchronize the scrolling for the 2
	 * list views when searching for a future trip.
	 *
	 * Logical basis for syncing 2 scrolls on:
	 * https://stackoverflow.com/questions/31372341/how-can-i-make-two-listviews-scroll-together-in-javafx
	 * Posted By: ItachiUchiha
	 */
	private void setSyncScrollSearchTrip() {
		Node n1 = searchDisplay.lookup(".scroll-bar");
		if (n1 instanceof ScrollBar) {
			final ScrollBar bar1 = (ScrollBar) n1;
			Node n2 = searchDisplay1.lookup(".scroll-bar");
			if (n2 instanceof ScrollBar) {
				final ScrollBar bar2 = (ScrollBar) n2;
				bar1.valueProperty().bindBidirectional(bar2.valueProperty());
			}
		}
	}

	/**
	 * Return to the main menu of the program. Can be used upon editing, map screen,
	 * and other selected windows of the program.
	 */
	@FXML
	public void goHome(){
		updateMainScreen();
		returnDefaultScreen();
		whichEdit = 0;
	}

	/**
	 * Enables parts of the GUI that are partof the main screen and disables all other parts
	 */
	private void returnDefaultScreen(){
		searchedLabel.setText("...");
		saveChangesButton.setVisible(false);
		discardChangesButton.setVisible(false);
		addTimeBtn.setVisible(false);
		subTimeBtn.setVisible(false);
		newTimeBtn.setVisible(false);
		editHBox.setVisible(false);
		idBox.setVisible(true);
		searchDisplay.setVisible(true);
		searchBarText.setVisible(true);
		searchedForLabel.setVisible(true);
		searchedLabel.setVisible(true);
		searchValueLabel.setVisible(true);
		searchMenu.setDisable(false);
		homeMenu.setDisable(true);
		upcomingTripLabel.setVisible(false);
		editStopMenuItem.setDisable(false);
		editTripMenuItem.setDisable(false);
		editStopTimeMenuItem.setDisable(false);
		editStopTimesByGroupMenuItem.setDisable(false);
		editRouteMenuItem.setDisable(false);
		tripIDList.setVisible(false);
		chooseTripLabel.setVisible(false);
		routesTextArea.setVisible(true);
		stopsTextArea.setVisible(true);
		stopTimesTextArea.setVisible(true);
		tripsTextArea.setVisible(true);
		fileMenu.setDisable(false);
		editMenu.setDisable(false);
		editStopTimeGroupButtons.setVisible(false);
	}

	/**
	 * Returns to the main load up screen without saving any edits
	 */
	@FXML
	public void discardEdits(){
		returnDefaultScreen();
		whichEdit = 0;
	}

	/**
	 * Method to determine which file is being edited.
	 * Then grabs the data from the list views to put/save
	 * back into the lists originally grabbed from.
	 */
	@FXML
	public void saveEdits(){
		boolean validData = true;
		switch(whichEdit){
			case 1:
				ArrayList<Route> routes = (ArrayList<Route>) routeObserver.getRoutes();
                Routes editRoutes = transitSystem.getRoutes();
				//Goes through line by line of the new editted array and the old array replacing it. 
				for(int i = 0; i < editRoute.size(); i++) {
					Route routeOld = routes.get(editRoute.get(i) - 1);
					//Pulling the information for the GUI edit lists and saving them as a new route.
					Route routeNew = new Route(editListOne.getItems().get(editRoute.get(i)), editListEight.getItems().get(editRoute.get(i)), editListThree.getItems().get(editRoute.get(i)), editListFour.getItems().get(editRoute.get(i)), editListFive.getItems().get(editRoute.get(i)), editListTwo.getItems().get(editRoute.get(i)), editListSix.getItems().get(editRoute.get(i)), editListSeven.getItems().get(editRoute.get(i)), editListNine.getItems().get(editRoute.get(i)));
					//Passed to the File handler class that was in charge of checking to make sure the data match specifications.
					//If it did save the data
                    if(handler.routesDataValidate(routeNew.toString().split(","))) {
                        editRoutes.deleteRoute(routeOld);
                        editRoutes.addRoute(routeNew);
                        editRoutes.sortRoutes();

						//All Routes are linked to trips, because a route is a list of trips. They need to be deleted so the new trips can be added
                        List<Trip> tripsOnOldRoute = transitSystem.getTrips().findTripsOnRoute(routeOld.getRouteID());
                        for(Trip trip : tripsOnOldRoute) {
                            tripsOnRoute.deleteRouteOnTrip(trip.getTripID(), routeOld);
                            tripsOnRoute.deleteTripOnRoute(routeOld.getRouteID(), trip);
                        }
						//Adding the new trips
						List<Trip> tripsOnNewRoute = transitSystem.getTrips().findTripsOnRoute(routeNew.getRouteID());
						for (Trip trip : tripsOnNewRoute) {
							tripsOnRoute.addRouteOnTrip(trip.getTripID(), routeNew);
							tripsOnRoute.addTripOnRoute(routeNew.getRouteID(), trip);
						}
					}else{
                        alertInvalidEdit();
                        validData = false;
					}
				}
				//Update the saved routes to make them the current routes
				transitSystem.setAllRoutes(editRoutes);
				editRoute.clear();
				break;
			case 2:
				ArrayList<Stop> stops = (ArrayList<Stop>) stopObserver.getStops();
				Stops editStops = transitSystem.getStops();
				for(int i = 0; i < editStop.size(); i++){
					Stop stopOld = stops.get(editStop.get(i) - 1);
					Stop stopNew = new Stop(editListOne.getItems().get(editStop.get(i)), editListTwo.getItems().get(editStop.get(i)), editListThree.getItems().get(editStop.get(i)), editListFive.getItems().get(editStop.get(i)), editListFour.getItems().get(editStop.get(i)));

                    if(handler.stopsDataValidate(stopNew.toString().split(","))){
                        editStops.deleteStop(stopOld);
                        editStops.addStop(stopNew);
                        editStops.sortStops();

                        List<Trip> tripsWithOldStop = stopsOnTrip.searchForTripsOnStop(stopOld.getStopID());
                        for (int j = 0; j < tripsWithOldStop.size(); j++) {
                            stopsOnTrip.deleteStopOnTrip(tripsWithOldStop.get(j).getTripID(), stopOld);
                            stopsOnTrip.deleteTripOnStop(stopOld.getStopID(), tripsWithOldStop.get(j));
                        }

                        List<String> tripIDs = transitSystem.getStopTimes().searchTripIDsByStopID(stopNew.getStopID());
                        for (String tripID : tripIDs) {
                            stopsOnTrip.addStopOnTrip(tripID, stopNew);
                            stopsOnTrip.addTripOnStop(stopNew.getStopID(), transitSystem.getTrips().searchTrip(tripID));
                        }
					}else{
                        alertInvalidEdit();
                        validData = false;
					}
				}
				transitSystem.setAllStops(editStops);
				editStop.clear();
				break;
			case 3:
				ArrayList<StopTime> stopTimes = (ArrayList<StopTime>) stopTimeObserver.getStopTimes();
                StopTimes editStopTimes = transitSystem.getStopTimes();
				//StopTimes editStopTimes = transitSystem.getStopTimes();
                for(int i = 0; i < editStopTime.size(); i++) {
					StopTime stopTimeOld = stopTimes.get(editStopTime.get(i) - 1);
					StopTime stopTimeNew = new StopTime(editListOne.getItems().get(editStopTime.get(i)), editListTwo.getItems().get(editStopTime.get(i)), editListThree.getItems().get(editStopTime.get(i)), editListFour.getItems().get(editStopTime.get(i)), editListFive.getItems().get(editStopTime.get(i)), editListSix.getItems().get(editStopTime.get(i)), editListSeven.getItems().get(editStopTime.get(i)), editListEight.getItems().get(editStopTime.get(i)));

                    if(handler.stopTimesDataValidate(stopTimeNew.toString().split(","))){
                        editStopTimes.deleteStopTime(stopTimeOld);
                        editStopTimes.addStopTime(stopTimeNew);

                        stopsOnTrip.deleteStopTimeOnTrip(stopTimeOld.getTripID(), stopTimeOld);

                        stopsOnTrip.addStopTimeOnTrip(stopTimeNew.getTripID(), stopTimeNew);
                    }else{
                        alertInvalidEdit();
                        validData = false;
                    }

                }
                transitSystem.setAllStopTimes(editStopTimes);
				editStopTime.clear();
				break;
			case 4:
				ArrayList<Trip> trips = (ArrayList<Trip>) tripObserver.getTrips();
				Trips editTrips = transitSystem.getTrips();

				for(int i = 0; i < editTrip.size(); i++) {
                    Trip tripOld = trips.get(editTrip.get(i) - 1);
                    Trip tripNew = new Trip(editListOne.getItems().get(editTrip.get(i)), editListTwo.getItems().get(editTrip.get(i)), editListThree.getItems().get(editTrip.get(i)), editListFour.getItems().get(editTrip.get(i)), editListFive.getItems().get(editTrip.get(i)), editListSix.getItems().get(editTrip.get(i)), editListSeven.getItems().get(editTrip.get(i)));

                    if(handler.tripsDataValidate(tripNew.toString().split(","))){
                        editTrips.deleteTrip(tripOld);
                        editTrips.addTrip(tripNew);
                        editTrips.sortTrips();

                        tripsOnRoute.deleteTripOnRoute(tripOld.getRouteID(), tripOld);
                        tripsOnRoute.deleteRouteOnTrip(tripOld.getTripID(), transitSystem.getRoutes().searchRoute(tripOld.getRouteID()));



                        List<StopTime> stopTimesOnOldTrip = transitSystem.getStopTimes().findStopTimesOnTrip(tripOld.getTripID());
                        List<Stop> stopsOnOldTrip = new ArrayList<>();
                        for(StopTime stopTime : stopTimesOnOldTrip) {
                            stopsOnOldTrip.add(transitSystem.getStops().searchStop(stopTime.getStopID()));
                        }
                        for (int j = 0; j < stopTimesOnOldTrip.size(); j++) {
                            stopsOnTrip.deleteStopTimeOnTrip(tripOld.getTripID(), stopTimesOnOldTrip.get(j));
                        }
                        for (int j = 0; j < stopsOnOldTrip.size(); j++) {
                            stopsOnTrip.deleteStopOnTrip(tripOld.getTripID(), stopsOnOldTrip.get(j));
                            stopsOnTrip.deleteTripOnStop(stopsOnOldTrip.get(j).getStopID(), tripOld);
                        }



                        tripsOnRoute.addTripOnRoute(tripNew.getRouteID(), tripNew);
                        tripsOnRoute.addRouteOnTrip(tripNew.getTripID(), transitSystem.getRoutes().searchRoute(tripNew.getRouteID()));

					    List<StopTime> stopTimesOnNewTrip = transitSystem.getStopTimes().findStopTimesOnTrip(tripNew.getTripID());
                        List<Stop> stopsOnNewTrip = new ArrayList<>();
                        for(StopTime stopTime : stopTimesOnNewTrip) {
                            stopsOnNewTrip.add(transitSystem.getStops().searchStop(stopTime.getStopID()));
                        }
                        for (int j = 0; j < stopTimesOnNewTrip.size(); j++) {
                            stopsOnTrip.addStopTimeOnTrip(tripNew.getTripID(), stopTimesOnNewTrip.get(j));
                        }
                        for (int j = 0; j < stopsOnNewTrip.size(); j++) {
                            stopsOnTrip.addStopOnTrip(tripNew.getTripID(), stopsOnNewTrip.get(j));
                            stopsOnTrip.addTripOnStop(stopsOnNewTrip.get(j).getStopID(), tripNew);
                        }
					}else{
                        alertInvalidEdit();
                        validData = false;
					}
				}
				transitSystem.setAllTrips(editTrips);
				editTrip.clear();
				break;
			default:
				break;
		}

		if(validData) {
			whichEdit = 0;
		} else {
			if(whichEdit == 1){
				whichEdit = 0;
				editRoutes();
			} else if(whichEdit == 2){
				whichEdit = 0;
				editStops();
			} else if(whichEdit == 3){
				whichEdit = 0;
				editStopTimes();
			} else {
				//if whichEdit = 4
				whichEdit = 0;
				editTrips();
			}
		}
	}

	/**
	 * Enable the GUI for editing the stoptimes.
	 */
	@FXML
	public void editStopTimesByGroup(){
		editStopMenuItem.setDisable(true);
		editTripMenuItem.setDisable(true);
		editStopTimeMenuItem.setDisable(true);
		editRouteMenuItem.setDisable(true);
		searchBarText.setVisible(true);
		searchValueLabel.setVisible(true);
		homeMenu.setDisable(false);
		routesTextArea.setVisible(false);
		stopsTextArea.setVisible(false);
		stopTimesTextArea.setVisible(false);
		tripsTextArea.setVisible(false);
		searchDisplay.setVisible(false);
		searchedLabel.setVisible(false);
		searchedForLabel.setVisible(false);
		searchValueLabel.setVisible(false);
		searchBarText.setVisible(false);
		searchMenu.setDisable(true);
		fileMenu.setDisable(true);
		editMenu.setDisable(true);

		//Where the trip ids are displayed
		//editHBox.setVisible(true);
		//editListOne.setVisible(true);
		tripIDList.setVisible(true);
		chooseTripLabel.setVisible(true);
		ArrayList<Trip> trips = (ArrayList<Trip>) transitSystem.getTripCollection();
		ArrayList<String> tripsId = new ArrayList<>();
		tripsId.add("Trip Ids");

		//Get and display the trip Ids associated to the stops
		for(int i = 0; i < trips.size(); i++){
			tripsId.add(trips.get(i).getTripID());
		}
		ObservableList<String> tripsList = FXCollections.observableArrayList(tripsId);
		tripIDList.setItems(tripsList);
		whichEdit = 3;

	}

	/**
	 * Method to display the route file in list views
	 * and able to edit within the GUI
	 */
	@FXML
	public void editRoutes() {
		editStopMenuItem.setDisable(true);
		editTripMenuItem.setDisable(true);
		editStopTimeMenuItem.setDisable(true);
		editStopTimesByGroupMenuItem.setDisable(true);
		if (whichEdit == 0) {
			setEditBox();
			ArrayList<Route> routeList = (ArrayList<Route>) routeObserver.getRoutes();
			ArrayList<String> routeIds = new ArrayList<>();
			ArrayList<String> routeTypes = new ArrayList<>();
			ArrayList<String> routeShort = new ArrayList<>();
			ArrayList<String> routeLong = new ArrayList<>();
			ArrayList<String> routeDesc = new ArrayList<>();
			ArrayList<String> routeUrl = new ArrayList<>();
			ArrayList<String> routeColor = new ArrayList<>();
			ArrayList<String> routeAgency = new ArrayList<>();
			ArrayList<String> routeTextColor = new ArrayList<>();

			routeIds.add("Route Ids");
			routeTypes.add("Route Types");
			routeShort.add("Route Short");
			routeLong.add("Route Long");
			routeDesc.add("Route Desc");
			routeUrl.add("Route URL");
			routeColor.add("Route Color");
			routeAgency.add("Route Agency");
			routeTextColor.add("Route Text Color");

			for (int i = 0; i < routeList.size(); i++) {
				Route route = routeList.get(i);
				routeIds.add(route.getRouteID());
				routeTypes.add(route.getRouteType());
				routeShort.add(route.getRouteShortName());
				routeLong.add(route.getRouteLongName());
				routeDesc.add(route.getRouteDesc());
				routeUrl.add(route.getRouteURL());
				routeColor.add(route.getRouteColor());
				routeAgency.add(route.getAgencyID());
				routeTextColor.add(route.getRouteTextColor());
			}

			ObservableList<String> ids = FXCollections.observableArrayList(routeIds);
			ObservableList<String> types = FXCollections.observableArrayList(routeTypes);
			ObservableList<String> shorts = FXCollections.observableArrayList(routeShort);
			ObservableList<String> longs = FXCollections.observableArrayList(routeLong);
			ObservableList<String> decs = FXCollections.observableArrayList(routeDesc);
			ObservableList<String> urls = FXCollections.observableArrayList(routeUrl);
			ObservableList<String> colors = FXCollections.observableArrayList(routeColor);
			ObservableList<String> agencys = FXCollections.observableArrayList(routeAgency);
			ObservableList<String> textColor = FXCollections.observableArrayList(routeTextColor);

			editListOne.setItems(ids);
			editListTwo.setItems(types);
			editListThree.setItems(shorts);
			editListFour.setItems(longs);
			editListFive.setItems(decs);
			editListSix.setItems(urls);
			editListSeven.setItems(colors);
			editListEight.setItems(agencys);
			editListNine.setItems(textColor);

			setListViewsEditable(editListOne);
			setListViewsEditable(editListTwo);
			setListViewsEditable(editListThree);
			setListViewsEditable(editListFour);
			setListViewsEditable(editListFive);
			setListViewsEditable(editListSix);
			setListViewsEditable(editListSeven);
			setListViewsEditable(editListEight);
			setListViewsEditable(editListNine);
			whichEdit = 1;
		}else{
			
		}
	}
	/**
	 * Method to display the stops file in list views
	 * and able to edit within the GUI
	 */
	@FXML
	public void editStops(){
		editRouteMenuItem.setDisable(true);
		editTripMenuItem.setDisable(true);
		editStopTimeMenuItem.setDisable(true);
		editStopTimesByGroupMenuItem.setDisable(true);
		if(whichEdit == 0) {
			setEditBox();
			editListSix.setVisible(false);
			editListSeven.setVisible(false);
			editListEight.setVisible(false);
			editListNine.setVisible(false);
			ArrayList<Stop> stopList = (ArrayList<Stop>) stopObserver.getStops();
			ArrayList<String> stopIds = new ArrayList<>();
			ArrayList<String> stopNames = new ArrayList<>();
			ArrayList<String> stopDecs = new ArrayList<>();
			ArrayList<String> stopLong = new ArrayList<>();
			ArrayList<String> stopLat = new ArrayList<>();

			stopIds.add("Stop Ids");
			stopNames.add("Stop names");
			stopDecs.add("Stop Description");
			stopLong.add("Stop Long");
			stopLat.add("Stop Lat");

			for (int i = 0; i < stopList.size(); i++) {
				Stop stop = stopList.get(i);
				stopIds.add(stop.getStopID());
				stopNames.add(stop.getStopName());
				stopDecs.add(stop.getStopDesc());
				stopLong.add(stop.getStopLon());
				stopLat.add(stop.getStopLat());
			}

			ObservableList<String> ids = FXCollections.observableArrayList(stopIds);
			ObservableList<String> names = FXCollections.observableArrayList(stopNames);
			ObservableList<String> decs = FXCollections.observableArrayList(stopDecs);
			ObservableList<String> longs = FXCollections.observableArrayList(stopLong);
			ObservableList<String> lats = FXCollections.observableArrayList(stopLat);
			editListOne.setItems(ids);
			editListTwo.setItems(names);
			editListThree.setItems(decs);
			editListFour.setItems(longs);
			editListFive.setItems(lats);
			setListViewsEditable(editListOne);
			setListViewsEditable(editListTwo);
			setListViewsEditable(editListThree);
			setListViewsEditable(editListFour);
			setListViewsEditable(editListFive);

			whichEdit = 2;
		}else{
			
		}
	}

	/**
	 * Enable the GUI for viewing and editing the stop times information
	 */
	@FXML
	public void editStopTimes(){
		editStopMenuItem.setDisable(true);
		editTripMenuItem.setDisable(true);
		editRouteMenuItem.setDisable(true);
		editStopTimesByGroupMenuItem.setDisable(true);
		if(whichEdit == 0) {
			setEditBox();
			editListNine.setVisible(false);
			ArrayList<StopTime> stopTimeList = (ArrayList<StopTime>) stopTimeObserver.getStopTimes();
			showStopTimeData(stopTimeList);

			setListViewsEditable(editListOne);
			setListViewsEditable(editListTwo);
			setListViewsEditable(editListThree);
			setListViewsEditable(editListFour);
			setListViewsEditable(editListFive);
			setListViewsEditable(editListSix);
			setListViewsEditable(editListSeven);
			setListViewsEditable(editListEight);

			whichEdit = 3;
		}else{
			
		}
	}

	/**
	 * A Method to handle displaying the stop time information.
	 * @param stopTimeList A list of stop dimes to display.
	 */
	private void showStopTimeData(ArrayList<StopTime> stopTimeList) {
		int size = stopTimeList.size() + 100;
		//ArrayList<StopTime> stopTimeList = (ArrayList<StopTime>) stopTimeObserver.getStopTimes();
		ArrayList<String> tripIDs = new ArrayList<>(size);
		ArrayList<String> arrival_times = new ArrayList<>(size);
		ArrayList<String> departure_times = new ArrayList<>(size);
		ArrayList<String> stopIDs = new ArrayList<>(size);
		ArrayList<String> stopSequences = new ArrayList<>(size);
		ArrayList<String> stopHeadsigns = new ArrayList<>(size);
		ArrayList<String> pickup_types = new ArrayList<>(size);
		ArrayList<String> dropoff_types = new ArrayList<>(size);

		tripIDs.add("Trip IDs");
		arrival_times.add("Arrival Times");
		departure_times.add("Departure Times");
		stopIDs.add("Stop IDs");
		stopSequences.add("Stop Sequences");
		stopHeadsigns.add("Stop Headsigns");
		pickup_types.add("Pickup Types");
		dropoff_types.add("Dropoff Types");

		for (int i = 0; i < stopTimeList.size(); i++) {
			StopTime stopTime = stopTimeList.get(i);
			tripIDs.add(stopTime.getTripID());
			arrival_times.add(stopTime.getArrivalTime());
			departure_times.add(stopTime.getDepartureTime());
			stopIDs.add(stopTime.getStopID());
			stopSequences.add((stopTime.getStopSequence()));
			stopHeadsigns.add(stopTime.getStopHeadsign());
			pickup_types.add(stopTime.getPickupType());
			dropoff_types.add(stopTime.getDropOffType());
		}


		editListOne.setItems(FXCollections.observableArrayList(tripIDs));
		editListTwo.setItems(FXCollections.observableArrayList(arrival_times));
		editListThree.setItems(FXCollections.observableArrayList(departure_times));
		editListFour.setItems(FXCollections.observableArrayList(stopIDs));
		editListFive.setItems(FXCollections.observableArrayList(stopSequences));
		editListSix.setItems(FXCollections.observableArrayList(stopHeadsigns));
		editListSeven.setItems(FXCollections.observableArrayList(pickup_types));
		editListEight.setItems(FXCollections.observableArrayList(dropoff_types));
	}

	/**
	 * Display the GUI that allows the edit of of the trips
	 */
	@FXML
	public void editTrips(){
		editStopMenuItem.setDisable(true);
		editRouteMenuItem.setDisable(true);
		editStopTimeMenuItem.setDisable(true);
		editStopTimesByGroupMenuItem.setDisable(true);
		if(whichEdit == 0) {
			setEditBox();
			editListEight.setVisible(false);
			editListNine.setVisible(false);
			ArrayList<Trip> tripList = (ArrayList<Trip>) tripObserver.getTrips();
			ArrayList<String> routeIDs = new ArrayList<>();
			ArrayList<String> serviceIDs = new ArrayList<>();
			ArrayList<String> tripIDs = new ArrayList<>();
			ArrayList<String> tripHeadsigns = new ArrayList<>();
			ArrayList<String> directionIDs = new ArrayList<>();
			ArrayList<String> blockIDs = new ArrayList<>();
			ArrayList<String> shapeIDs = new ArrayList<>();

			routeIDs.add("Route IDs");
			serviceIDs.add("Service IDs");
			tripIDs.add("Trip IDs");
			tripHeadsigns.add("Trip Headsigns");
			directionIDs.add("Direction IDs");
			blockIDs.add("Block IDs");
			shapeIDs.add("Shape IDs");

			for (int i = 0; i < tripList.size(); i++) {
				Trip trip = tripList.get(i);
				routeIDs.add(trip.getRouteID());
				serviceIDs.add(trip.getServiceID());
				tripIDs.add(trip.getTripID());
				tripHeadsigns.add(trip.getTripHeadsign());
				directionIDs.add((trip.getDirectionID()));
				blockIDs.add(trip.getBlockID());
				shapeIDs.add(trip.getShapeID());
			}

			ObservableList<String> OBrouteIDs = FXCollections.observableArrayList(routeIDs);
			ObservableList<String> OBserviceIDs = FXCollections.observableArrayList(serviceIDs);
			ObservableList<String> OBtripIDs = FXCollections.observableArrayList(tripIDs);
			ObservableList<String> OBtripHeadsigns = FXCollections.observableArrayList(tripHeadsigns);
			ObservableList<String> OBdirectionIDs = FXCollections.observableArrayList(directionIDs);
			ObservableList<String> OBblockIDs = FXCollections.observableArrayList(blockIDs);
			ObservableList<String> OBshapeIDs = FXCollections.observableArrayList(shapeIDs);
			editListOne.setItems(OBrouteIDs);
			editListTwo.setItems(OBserviceIDs);
			editListThree.setItems(OBtripIDs);
			editListFour.setItems(OBtripHeadsigns);
			editListFive.setItems(OBdirectionIDs);
			editListSix.setItems(OBblockIDs);
			editListSeven.setItems(OBshapeIDs);
			setListViewsEditable(editListOne);
			setListViewsEditable(editListTwo);
			setListViewsEditable(editListThree);
			setListViewsEditable(editListFour);
			setListViewsEditable(editListFive);
			setListViewsEditable(editListSix);
			setListViewsEditable(editListSeven);

			whichEdit = 4;
		}else{
			
		}
	}

	/**
	 * Uses a requested stop for searching for the next trip containing it.
	 * TODO
	 */
	public void displayTransitInfo(){

	}


	/**
	 * Uses a requested routeID to search for any corresponding trips occurring in the future.
	 */
	@FXML
	public void searchRouteForFutureTrips() {
		if(!searchBarText.getText().isEmpty()) {
			String routeID = searchBarText.getText();
			searchDisplay.setVisible(true);
			searchDisplay1.setVisible(true);
			setSyncScrollSearchTrip();

			searchedForLabel.setText("Future trips for route ID: " + routeID);

			ArrayList<String> tripIDs = new ArrayList<>();
			tripIDs.add("Trip IDs");
			ArrayList<String> arrivalTimes = new ArrayList<>();
			arrivalTimes.add("Arrival Times");
			List<StopTime> stopTimeArrayList;
			int j;
			int u = 0;
			int timeOfTripArrival;

			//get arrival time compare with current time
			String currentHourString = getCurrentTime()[0];
			String currentMinuteString = getCurrentTime()[1];
			String currentSecondString = getCurrentTime()[2];
			int currentHourInt = Integer.parseInt(currentHourString);
			int currentMinutesInt = Integer.parseInt(currentMinuteString);
			int currentSecondsInt = Integer.parseInt(currentSecondString);
			if(currentMinutesInt < 10){
				currentMinuteString = "0" + currentMinuteString;
			}
			if(currentSecondsInt < 10){
				currentSecondString = "0" + currentSecondString;
			}
			int currentTimeInSeconds = (currentHourInt * 3600) + (currentMinutesInt * 60) + currentSecondsInt;

			try {
				List<Trip> tripList = tripsOnRoute.searchForTripsOnRoute(routeID);
				if (tripList.isEmpty()) {
					throw new InvalidIDException();
				}

				for(int i = 0; i < tripList.size(); i ++){
					stopTimeArrayList = stopsOnTrip.searchForStopTimesOnTrip(tripList.get(i).getTripID());
					String[] arrivalTime = stopTimeArrayList.get(0).getArrivalTime().split(":");
					int arrivalHour = Integer.parseInt(arrivalTime[0]);
					int arrivalMinute = Integer.parseInt(arrivalTime[1]);
					int arrivalSecond = Integer.parseInt(arrivalTime[2]);
					timeOfTripArrival = (arrivalHour * 3600) + (arrivalMinute * 60) + arrivalSecond;
					Trip trip = tripList.get(i);
					if (timeOfTripArrival > currentTimeInSeconds) {
						tripIDs.add(trip.getTripID());
						arrivalTimes.add(stopTimeArrayList.get(0).getArrivalTime());
					}
				}
				ObservableList<String> OBtripIDs = FXCollections.observableArrayList(tripIDs);
				ObservableList<String> OBarrivalTimes = FXCollections.observableArrayList(arrivalTimes);
				searchDisplay.setItems(OBtripIDs);
				searchDisplay1.setItems(OBarrivalTimes);
			} catch (InvalidIDException npe) {
				alertInvalidID();
				searchDisplay.setVisible(false);
				searchDisplay1.setVisible(false);
				searchedForLabel.setText("...");
			}
		} else {
			alertNoID();
			searchDisplay.setVisible(false);
			searchDisplay1.setVisible(false);
			searchedForLabel.setText("...");
		}
	}


	/**
	 * 
	 * @return
	 */
	private String[] getCurrentTime(){
		String[] timeArray = {Integer.toString(LocalDateTime.now().getHour()), Integer.toString(LocalDateTime.now().getMinute()),
				Integer.toString(LocalDateTime.now().getSecond())};
		return timeArray;
	}

	/**
	 * 
	 * @param closestTimeInSeconds
	 * @return
	 */
	private String[] convertTimeInSecondsToStringArray(int closestTimeInSeconds){
		String printableHours;
		String printableMinutes;
		String printableSeconds;
		int hours;
		int minutes;
		int seconds;
		int remainder;

		//Converting a time from just total seconds into hours and minutes
		hours = closestTimeInSeconds / 3600;
		remainder = closestTimeInSeconds - hours * 3600;
		minutes = remainder / 60;
		remainder = remainder - minutes * 60;
		seconds = remainder;
		printableHours = Integer.toString(hours);
		printableMinutes = Integer.toString(minutes);
		printableSeconds = Integer.toString(seconds);
		//Wanted to keep all time in a 00:00:00 format. If time was a single digit appended a 0 to the front.
		if(minutes < 10){
			printableMinutes = "0" + printableMinutes;
		}
		if(seconds < 10){
			printableSeconds = "0" + printableSeconds;
		}

		String[] timeArray = {printableHours, printableMinutes, printableSeconds};
		return timeArray;
	}

	/**
	 * Uses a requested stop for searching for the next trip containing it. (closest to current time)
	 */
	@FXML
	public void searchStopForNextTrip(){
		//If the seach bar isnt empty
		if(!searchBarText.getText().isEmpty()) {
			String stopID = searchBarText.getText();
			searchedForLabel.setText("Closest Upcoming Trip for Stop ID: " + stopID);
			upcomingTripLabel.setVisible(true);
			searchDisplay.setVisible(false);
			searchDisplay1.setVisible(false);

			List<StopTime> stopTimeArrayList;
			int j;
			int u = 0;
			int closestTimeInSeconds;
			int newClosestTimeInSeconds = 9999999;
			
			//get arrival time compare with current time
			String currentHourString = getCurrentTime()[0];
			String currentMinuteString = getCurrentTime()[1];
			String currentSecondString = getCurrentTime()[2];
			int currentHourInt = Integer.parseInt(currentHourString);
			int currentMinutesInt = Integer.parseInt(currentMinuteString);
			int currentSecondsInt = Integer.parseInt(currentSecondString);
			if(currentMinutesInt < 10){
				currentMinuteString = "0" + currentMinuteString;
			}
			if(currentSecondsInt < 10){
				currentSecondString = "0" + currentSecondString;
			}
			int currentTimeInSeconds = (currentHourInt * 3600) + (currentMinutesInt * 60) + currentSecondsInt;

			try {
				List<Trip> tripList = stopsOnTrip.searchForTripsOnStop(stopID);
				if (tripList.isEmpty()) {
					throw new InvalidIDException();
				}

				for(int i = 0; i < tripList.size(); i ++){
					stopTimeArrayList = stopsOnTrip.searchForStopTimesOnTrip(tripList.get(i).getTripID());
					j = 0;
					while(!stopTimeArrayList.get(j).getStopID().equals(searchBarText.getText())) {
						j++;
					}
					String[] arrivalTime = stopTimeArrayList.get(j).getArrivalTime().split(":");
					int arrivalHour = Integer.parseInt(arrivalTime[0]);
					int arrivalMinute = Integer.parseInt(arrivalTime[1]);
					int arrivalSecond = Integer.parseInt(arrivalTime[2]);
					closestTimeInSeconds = (arrivalHour * 3600) + (arrivalMinute * 60) + arrivalSecond;

					if(closestTimeInSeconds < newClosestTimeInSeconds && closestTimeInSeconds > currentTimeInSeconds){
						newClosestTimeInSeconds = closestTimeInSeconds;
						u = i;
					}
				}
				String[] printableTimeArray = convertTimeInSecondsToStringArray(newClosestTimeInSeconds);

				upcomingTripLabel.setText( "Trip:				" + tripList.get(u).getTripID() + "\n\n" +
											"Current Time:		" + currentHourString + ":" + currentMinuteString + ":" + currentSecondString + "\n\n" +
											"Arrival Time: 		" + printableTimeArray[0] + ":" + printableTimeArray[1] + ":" +printableTimeArray[2]);
			} catch (InvalidIDException npe) {
				alertInvalidID();
				upcomingTripLabel.setText("...");
				searchedForLabel.setText("...");
			}
		} else {
			alertNoID();
			upcomingTripLabel.setText("...");
			searchedForLabel.setText("...");
		}
	}

	/**
	 * A method that searches by trip ID for all stop times associated with it.
	 */
	@FXML
	public void searchTripIDForArrivals(){
		upcomingTripLabel.setVisible(false);
		searchDisplay.setVisible(true);
		searchDisplay1.setVisible(false);
        try{
            String tripID = searchBarText.getText();
            if (tripID.isEmpty()) {
                throw new NullPointerException();
            }
            List<StopTime> stopTimeList = stopsOnTrip.searchForStopTimesOnTrip(tripID);

            if (stopTimeList.isEmpty()) {
                throw new InvalidIDException();
            }
            ArrayList<String> stopTimeArrayList = new ArrayList<>();
            for (int i = 0; i < stopTimeList.size(); i++) {
                stopTimeArrayList.add(stopTimeList.get(i).getArrivalTime());
            }
            searchedForLabel.setText("Arrival Times with the Trip ID: " + searchBarText.getText());
            ObservableList<String> timeList = FXCollections.observableArrayList(stopTimeArrayList);
            searchDisplay.setItems(timeList);
        }  catch (NullPointerException ex) {
            alertNoID();
            searchDisplay.setVisible(false);
			searchedForLabel.setText("...");
        }  catch (InvalidIDException ex) {
            alertInvalidID();
            searchDisplay.setVisible(false);
			searchedForLabel.setText("...");
        }
	}


	/**
	 * A method that searches by stop ID for all route ID's associated with it.
	 */
	@FXML
	public void searchStopIDForRoute() {
		upcomingTripLabel.setVisible(false);
		searchDisplay.setVisible(true);
		searchDisplay1.setVisible(false);
        try {
            String stopID = searchBarText.getText();
            if (stopID.isEmpty()) {
                throw new NullPointerException();
            }

            List<Trip> tripList = stopsOnTrip.searchForTripsOnStop(stopID);
            if (tripList.isEmpty()) {
                throw new InvalidIDException();
            }
            // Using a Set for Route ID's to prevent duplicates
            Set<String> routeIDList = new HashSet<>();
            for (int i = 0; i < tripList.size(); i++) {
                routeIDList.add(tripList.get(i).getRouteID());
            }
            searchedForLabel.setText("Routes with the Stop ID: " + searchBarText.getText());
            ObservableList<String> idList = FXCollections.observableArrayList(routeIDList);
            searchDisplay.setItems(idList);
        } catch (NullPointerException ex) {
            alertNoID();
            searchDisplay.setVisible(false);
			searchedForLabel.setText("...");
        } catch (InvalidIDException ex) {
            alertInvalidID();
            searchDisplay.setVisible(false);
			searchedForLabel.setText("...");
        }

	}

	@FXML
	public void addTime(){

	}

	@FXML
	public void subTime(){

	}

	@FXML
	public void newTime(){

	}


	@FXML
	public void searchBoxHandler(){
		//The tripsID for search
		String tripId = searchValueLabel.getId();


		//After the trip id is selected
		setEditBox();
		addTimeBtn.setVisible(true);
		subTimeBtn.setVisible(true);
		newTimeBtn.setVisible(true);
	}

	/**
	 * A method that searches by route ID for all stop ID's associated with it
	 */
	@FXML
	public void searchRouteIDForStops(){
		upcomingTripLabel.setVisible(false);
		searchDisplay.setVisible(true);
		searchDisplay1.setVisible(false);
        try {
            String routeID = searchBarText.getText();
            if (routeID.isEmpty()) {
                throw new NullPointerException();
            }

            List<Trip> tripList = tripsOnRoute.searchForTripsOnRoute(routeID);
            if (tripList.isEmpty()) {
                throw new InvalidIDException();
            }
            // Using a Set for Stop ID's to prevent duplicates
            Set<String> stopIDList = new HashSet<>();
            for (int i = 0; i < tripList.size(); i++) {
                List<StopTime> stopTimeList = stopsOnTrip.searchForStopTimesOnTrip(tripList.get(i).getTripID());
                for (int j = 0; j < stopTimeList.size(); j++) {
                    stopIDList.add(stopTimeList.get(j).getStopID());
                }
            }
            searchedForLabel.setText("Stop IDs on Route " + searchBarText.getText());
            ObservableList<String> idList = FXCollections.observableArrayList(stopIDList);
            searchDisplay.setItems(idList);
        } catch (NullPointerException ex) {
            alertNoID();
            searchDisplay.setVisible(false);
            searchedForLabel.setText("...");
        } catch (InvalidIDException ex) {
            alertInvalidID();
            searchDisplay.setVisible(false);
			searchedForLabel.setText("...");
        }

	}

	@FXML
	public void editStartOne(){
		setCurrentIndexEdit(editListOne);
	}

	@FXML
	public void editStartTwo(){
		setCurrentIndexEdit(editListTwo);
	}

	@FXML
	public void editStartThree(){
		setCurrentIndexEdit(editListThree);
	}


	@FXML
	public void editStartFour(){
		setCurrentIndexEdit(editListFour);
	}

	@FXML
	public void editStartFive(){
		setCurrentIndexEdit(editListFive);
	}

	@FXML
	public void editStartSix(){
		setCurrentIndexEdit(editListSix);
	}

	@FXML
	public void editStartSeven(){
		setCurrentIndexEdit(editListSeven);
	}

	@FXML
	public void editStartEight(){
		setCurrentIndexEdit(editListEight);
	}

	@FXML
	public void editStartNine(){
		setCurrentIndexEdit(editListNine);
	}

	/**
	 * Simple method that takes what ever list is passed and takes the current editing index
	 * and saves it in the appropriate arraylist
	 *
	 * @param list Passed in list that is currently being edited
	 */
	private void setCurrentIndexEdit(ListView list){
		switch (whichEdit) {
			case 1:
				if (editRoute.contains(list.getEditingIndex())) {
					editRoute.remove(editRoute.indexOf(list.getEditingIndex()));
					editRoute.add(list.getEditingIndex());
				}else{
					editRoute.add(list.getEditingIndex());
				}
				break;
			case 2:
				if (editStop.contains(list.getEditingIndex())) {
					editStop.remove(editStop.indexOf(list.getEditingIndex()));
					editStop.add(list.getEditingIndex());
				}else{
					editStop.add(list.getEditingIndex());
				}
				break;
			case 3:
				if (editStopTime.contains(list.getEditingIndex())) {
					editStopTime.remove(editStopTime.indexOf(list.getEditingIndex()));
					editStopTime.add(list.getEditingIndex());
				}else{
					editStopTime.add(list.getEditingIndex());
				}
				break;
			case 4:
				if (editTrip.contains(list.getEditingIndex())) {
					editTrip.remove(editTrip.indexOf(list.getEditingIndex()));
					editTrip.add(list.getEditingIndex());
				}else{
					editTrip.add(list.getEditingIndex());
				}
				break;
			default:
				//TODO when they edit with no edit selected?
				break;
		}
	}

	/**
	 * Sets the list view passed in to editable
	 *
	 * @param test A list view
	 */
	private void setListViewsEditable(ListView test){
		test.setCellFactory(TextFieldListCell.forListView());
		test.setEditable(true);
	}

	/**
	 * Sets the list view passed in to not editable.
	 *
	 * @param test A list view
	 */
	private void setListViewsNotEditable(ListView test){
		test.setCellFactory(TextFieldListCell.forListView());
		test.setEditable(false);
	}

	public void tripIDClicked(MouseEvent mouseEvent) {
		currentTripID = tripIDList.getSelectionModel().getSelectedItem();
		//String clickedTrip =  tripIDList.getSelectionModel().getSelectedItem();
		if (!currentTripID.equals("Trip Ids")) {
			chooseTripLabel.setVisible(false);
			tripIDList.setVisible(false);
			ArrayList<StopTime> stopTimes = (ArrayList<StopTime>)transitSystem.getStopTimes().findStopTimesOnTrip(currentTripID);

			setEditBox();
			editListNine.setVisible(false);
			editStopTimeGroupButtons.setVisible(true);
			showStopTimeData(stopTimes);

			setListViewsNotEditable(editListOne);
			setListViewsNotEditable(editListTwo);
			setListViewsNotEditable(editListThree);
			setListViewsNotEditable(editListFour);
			setListViewsNotEditable(editListFive);
			setListViewsNotEditable(editListSix);
			setListViewsNotEditable(editListSeven);
			setListViewsNotEditable(editListEight);

			//whichEdit = 3;
		}

	}

	public void subtractArrivalTimes(ActionEvent actionEvent) {
		Dialog<int[]> dialog = new Dialog<>();
		dialog.setTitle("Subtact Time to all Arrival Times");
		dialog.setHeaderText("Enter a time to be subtracted to all arrival times");
		dialog.setResizable(true);
		Label amtAdd = new Label("Time to subtract: ");
		TextField textField = new TextField();
		textField.setText("00:00:00");
		GridPane grid = new GridPane();
		grid.add(amtAdd, 1, 1);
		grid.add(textField, 2, 1);
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, int[]>() {
			@Override
			public int[] call(ButtonType b) {
				String stringNum = textField.getText();
				if(stringNum.equals(null)){
					return null;
				}
				return timeToInt(stringNum);
			}
		});

		Optional<int[]> result = dialog.showAndWait();
		int[] addAmt = new int[]{};
		if (result.isPresent()) {
			addAmt = result.get();
		}
		ObservableList<String> arrivalTimes = editListTwo.getItems();
		ObservableList<String> departureTimes = editListThree.getItems();

		for(int i = 1; i < arrivalTimes.size(); i++) {
			int [] arrivalTimeInt = timeToInt(arrivalTimes.get(i));
			int [] departureTimeInt = timeToInt(departureTimes.get(i));
			if(arrivalTimeInt[2] < addAmt[2]){
				arrivalTimeInt[2] = (arrivalTimeInt[2] + 60) - addAmt[2];
				arrivalTimeInt[1] = arrivalTimeInt[1] -1;
			}else{
				arrivalTimeInt[2] = arrivalTimeInt[2] - addAmt[2];
			}
			if(arrivalTimeInt[1] < addAmt[1]){
				arrivalTimeInt[1] = (arrivalTimeInt[1] + 60) - addAmt[1];
				arrivalTimeInt[0] = arrivalTimeInt[0] -1;
			}else{
				arrivalTimeInt[1] = arrivalTimeInt[1] - addAmt[1];
			}
			if(arrivalTimeInt[0] < addAmt[0] || arrivalTimeInt[0] <= 0){
				return;
			}else{
				arrivalTimeInt[0] = arrivalTimeInt[0] - addAmt[0];
			}

			if(arrivalTimeInt[0] > departureTimeInt[0]){
				return;
			}else if(arrivalTimeInt[0] == 0 && arrivalTimeInt [1] >  departureTimeInt[1]){
				return;
			}
			if(i < 1) {
				int[] departureTimeIntFut = timeToInt(departureTimes.get(i-1));
				if(arrivalTimeInt[0] < departureTimeIntFut[0]){
					return;
				}else if(arrivalTimeInt[0] == 0 && arrivalTimeInt [1] <  departureTimeIntFut[1]){
					return;
				}
			}
			String formattedTime = String.format("%01d", arrivalTimeInt[0]) + ":" + String.format("%02d", arrivalTimeInt[1]) + ":" + String.format("%02d", arrivalTimeInt[2]);
			arrivalTimes.set(i,formattedTime);
		}
		editListTwo.setItems(arrivalTimes);
		whichEdit = 3;
		for(int i = 1; i < arrivalTimes.size(); i++){
			editStopTime.add(i);
		}
	}

	public void addArrivalTimes(ActionEvent actionEvent) {
		Dialog<int[]> dialog = new Dialog<>();
		dialog.setTitle("Add Time to all Arrival Times");
		dialog.setHeaderText("Enter a time to be added to all arrival times");
		dialog.setResizable(true);
		Label amtAdd = new Label("Time to add: ");
		TextField textField = new TextField();
		textField.setText("00:00:00");
		GridPane grid = new GridPane();
		grid.add(amtAdd, 1, 1);
		grid.add(textField, 2, 1);
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, int[]>() {
			@Override
			public int[] call(ButtonType b) {
				String stringNum = textField.getText();
				if(stringNum.equals(null)){
					return null;
				}
				return timeToInt(stringNum);
			}
		});

		Optional<int[]> result = dialog.showAndWait();
		int[] addAmt = new int[]{};
		if (result.isPresent()) {
			addAmt = result.get();
		}
		ObservableList<String> arrivalTimes = editListTwo.getItems();
		ObservableList<String> departureTimes = editListThree.getItems();

		for(int i = 1; i < arrivalTimes.size(); i++){
			int[] departureTimeInt = timeToInt(departureTimes.get(i));
			int[] arrivalTimeInt = timeToInt(arrivalTimes.get(i));
			arrivalTimeInt[0] = arrivalTimeInt[0] + addAmt[0];
			arrivalTimeInt[1] = arrivalTimeInt[1] + addAmt[1];
			arrivalTimeInt[2] = arrivalTimeInt[2] + addAmt[2];
			if(arrivalTimeInt[2] >= 60){
				arrivalTimeInt[2] = arrivalTimeInt[2] - 60;
				arrivalTimeInt[1] = arrivalTimeInt[1] + 1;
			}
			if(arrivalTimeInt[1] >= 60){
				arrivalTimeInt[1] = arrivalTimeInt[1] - 60;
				arrivalTimeInt[0] = arrivalTimeInt[0] + 1;
			}
			if(arrivalTimeInt[0] > 24){
				arrivalTimeInt[0] = arrivalTimeInt[0] - 24;
			}
			if(arrivalTimeInt[0] > departureTimeInt[0]){
				return;
			}else if(arrivalTimeInt[0] == 0 && arrivalTimeInt [1] >  departureTimeInt[1]){
				return;
			}
			if(i < departureTimes.size()-1) {
				int[] departureTimeIntFut = timeToInt(departureTimes.get(i+1));
				if(arrivalTimeInt[0] > departureTimeIntFut[0]){
					return;
				}else if(arrivalTimeInt[0] == 0 && arrivalTimeInt [1] >  departureTimeIntFut[1]){
					return;
				}
			}
			String formattedTime = String.format("%01d", arrivalTimeInt[0]) + ":" + String.format("%02d", arrivalTimeInt[1]) + ":" + String.format("%02d", arrivalTimeInt[2]);
			arrivalTimes.set(i,formattedTime);
		}
		editListTwo.setItems(arrivalTimes);
		whichEdit = 3;
		for(int i = 1; i < arrivalTimes.size(); i++){
			editStopTime.add(i);
		}
	}

	public void subtractDepartureTimes(ActionEvent actionEvent) {
		Dialog<int[]> dialog = new Dialog<>();
		dialog.setTitle("Subtact Time to all departure Times");
		dialog.setHeaderText("Enter a time to be subtracted to all departure times");
		dialog.setResizable(true);
		Label amtAdd = new Label("Time to subtract: ");
		TextField textField = new TextField();
		textField.setText("00:00:00");
		GridPane grid = new GridPane();
		grid.add(amtAdd, 1, 1);
		grid.add(textField, 2, 1);
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, int[]>() {
			@Override
			public int[] call(ButtonType b) {
				String stringNum = textField.getText();
				if(stringNum.equals(null)){
					return null;
				}
				return timeToInt(stringNum);
			}
		});

		Optional<int[]> result = dialog.showAndWait();
		int[] addAmt = new int[]{};
		if (result.isPresent()) {
			addAmt = result.get();
		}
		ObservableList<String> arrivalTimes = editListTwo.getItems();
		ObservableList<String> departureTimes = editListThree.getItems();

		for(int i = 1; i < departureTimes.size(); i++) {
			int[] departureTimeInt = timeToInt(departureTimes.get(i));
			int[] arrivalTimeInt = timeToInt(arrivalTimes.get(i));
			if(departureTimeInt[2] < addAmt[2]){
				departureTimeInt[2] = (departureTimeInt[2] + 60) - addAmt[2];
				departureTimeInt[1] = departureTimeInt[1] -1;
			}else{
				departureTimeInt[2] = departureTimeInt[2] - addAmt[2];
			}
			if(departureTimeInt[1] < addAmt[1]){
				departureTimeInt[1] = (departureTimeInt[1] + 60) - addAmt[1];
				departureTimeInt[0] = departureTimeInt[0] -1;
			}else{
				departureTimeInt[1] = departureTimeInt[1] - addAmt[1];
			}
			if(departureTimeInt[0] < addAmt[0] || departureTimeInt[0] <= 0){
				return;
			}else{
				departureTimeInt[0] = departureTimeInt[0] - addAmt[0];
			}
			if(arrivalTimeInt[0] > departureTimeInt[0]){
				return;
			}else if(arrivalTimeInt[0] == 0 && arrivalTimeInt [1] >  departureTimeInt[1]){
				return;
			}
			if(i < 1) {
				int[] arrivalTimeIntFut = timeToInt(arrivalTimes.get(i-1));
				if(departureTimeInt[0] < arrivalTimeIntFut[0]){
					return;
				}else if(departureTimeInt[0] == 0 && departureTimeInt [1] <  arrivalTimeIntFut[1]){
					return;
				}
			}
			String formattedTime = String.format("%01d", departureTimeInt[0]) + ":" + String.format("%02d", departureTimeInt[1]) + ":" + String.format("%02d", departureTimeInt[2]);
			departureTimes.set(i,formattedTime);
		}
		editListThree.setItems(departureTimes);
		whichEdit = 3;
		for(int i = 1; i < departureTimes.size(); i++){
			editStopTime.add(i);
		}
	}

	public void addDepartureTimes(ActionEvent actionEvent) {
		Dialog<int[]> dialog = new Dialog<>();
		dialog.setTitle("Add Time to all Departure Times");
		dialog.setHeaderText("Enter a time to be added to all Departure times");
		dialog.setResizable(true);
		Label amtAdd = new Label("Time to add: ");
		TextField textField = new TextField();
		textField.setText("00:00:00");
		GridPane grid = new GridPane();
		grid.add(amtAdd, 1, 1);
		grid.add(textField, 2, 1);
		dialog.getDialogPane().setContent(grid);
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, int[]>() {
			@Override
			public int[] call(ButtonType b) {
				String stringNum = textField.getText();
				if(stringNum.equals(null)){
					return null;
				}
				return timeToInt(stringNum);
			}
		});

		Optional<int[]> result = dialog.showAndWait();
		int[] addAmt = new int[]{};
		if (result.isPresent()) {
			addAmt = result.get();
		}
		ObservableList<String> departureTimes = editListThree.getItems();
		ObservableList<String> arrivalTimes = editListTwo.getItems();

		for(int i = 1; i < departureTimes.size(); i++){
			int[] departureTimeInt = timeToInt(departureTimes.get(i));
			int[] arrivalTimeInt = timeToInt(arrivalTimes.get(i));
			departureTimeInt[0] = departureTimeInt[0] + addAmt[0];
			departureTimeInt[1] = departureTimeInt[1] + addAmt[1];
			departureTimeInt[2] = departureTimeInt[2] + addAmt[2];
			if(departureTimeInt[2] >= 60){
				departureTimeInt[2] = departureTimeInt[2] - 60;
				departureTimeInt[1] = departureTimeInt[1] + 1;
			}
			if(departureTimeInt[1] >= 60){
				departureTimeInt[1] = departureTimeInt[1] - 60;
				departureTimeInt[0] = departureTimeInt[0] + 1;
			}
			if(departureTimeInt[0] > 24){
				departureTimeInt[0] = departureTimeInt[0] - 24;
			}
			if(arrivalTimeInt[0] > departureTimeInt[0]){
				return;
			}else if(arrivalTimeInt[0] == 0 && arrivalTimeInt [1] >  departureTimeInt[1]){
				return;
			}
			if(i < arrivalTimes.size()-1) {
				int[] arrivalTimeIntFut = timeToInt(arrivalTimes.get(i+1));
				if(departureTimeInt[0] > arrivalTimeIntFut[0]){
					return;
				}else if(departureTimeInt[0] == 0 && departureTimeInt [1] >  arrivalTimeIntFut[1]){
					return;
				}
			}

			String formattedTime = String.format("%01d", departureTimeInt[0]) + ":" + String.format("%02d", departureTimeInt[1]) + ":" + String.format("%02d", departureTimeInt[2]);
			departureTimes.set(i,formattedTime);
		}
		editListThree.setItems(departureTimes);
		whichEdit = 3;
		for(int i = 1; i < departureTimes.size(); i++){
			editStopTime.add(i);
		}
	}

	private int[] timeToInt(String time){

		String[] timeSplit = time.split(":");
		int[] timeNum;
		if(timeSplit.length  == 3) {
			timeNum = new int[]{Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]), Integer.parseInt(timeSplit[2])};
		}else if(timeSplit.length == 2){
			timeNum = new int[]{Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]), 0};
		}else{
			timeNum = new int[]{Integer.parseInt(timeSplit[0]), 0, 0};
		}
		return timeNum;
	}

	public void addStopTime(ActionEvent actionEvent) {
		Dialog<StopTime> dialog = new Dialog<>();
		dialog.setTitle("Enter StopTime Data");
		dialog.setHeaderText("Enter all necessary StopTime data.");
		dialog.setResizable(true);
		Label label1 = new Label("TripID: " + currentTripID);
		Label label2 = new Label("ArrivalTime: ");
		Label label3 = new Label("DepartureTime: ");
		Label label4 = new Label("StopID: ");
		Label label5 = new Label("StopSequence: ");
		Label label6 = new Label("StopHeadsign: ");
		Label label7 = new Label("PickupType: ");
		Label label8 = new Label("DropOffType: ");
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		TextField text3 = new TextField();
		TextField text4 = new TextField();
		TextField text5 = new TextField();
		TextField text6 = new TextField();
		TextField text7 = new TextField();
		TextField text8 = new TextField();
		GridPane grid = new GridPane();

		grid.add(label1, 1, 1);
		//grid.add(text1, 2, 1);

		grid.add(label2, 1, 2);
		grid.add(text2, 2, 2);

		grid.add(label3, 1, 3);
		grid.add(text3, 2, 3);

		grid.add(label4, 1, 4);
		grid.add(text4, 2, 4);

		grid.add(label5, 1, 5);
		grid.add(text5, 2, 5);

		grid.add(label6, 1, 6);
		grid.add(text6, 2, 6);

		grid.add(label7, 1, 7);
		grid.add(text7, 2, 7);

		grid.add(label8, 1, 8);
		grid.add(text8, 2, 8);

		dialog.getDialogPane().setContent(grid);

		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);

		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		dialog.setResultConverter(new Callback<ButtonType, StopTime>() {
			@Override
			public StopTime call(ButtonType b) {
				if (b == buttonTypeOk) {

					StopTime stopTime = new StopTime(currentTripID, text2.getText(), text3.getText(), text4.getText(), text5.getText(), text6.getText(), text7.getText(), text8.getText());
					String[] stopTimeString = stopTime.toString().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					if (handler.stopTimesDataValidateGroupEdit(stopTimeString)) {
						return stopTime;
					}
					else {
						alertInvalidStopTimeData();
					}
				}
				return null;
			}
		});

		Optional<StopTime> result = dialog.showAndWait();

		if (result.isPresent()) {
			StopTime stopTime = result.get();

			ObservableList<String> departureTimes = editListThree.getItems();
			ObservableList<String> arrivalTimes = editListTwo.getItems();
			ObservableList<String> stopSequences = editListFive.getItems();

			ObservableList<String> tripIDs = editListOne.getItems();
			ObservableList<String> stopIDs = editListFour.getItems();
			ObservableList<String> stopHeadsigns = editListSix.getItems();
			ObservableList<String> pickupTypes = editListSeven.getItems();
			ObservableList<String> dropOffTypes = editListEight.getItems();

			for (int i = 1; i < stopSequences.size(); i++ ) {
				editStopTime.add(i);
			}

			if (stopTime.getStopSequence().isEmpty()) {
				for (int i = 1; i < arrivalTimes.size(); i++) {
					if ((compareTimes(stopTime.getArrivalTime(), arrivalTimes.get(i)) > 0) && (compareTimes(stopTime.getArrivalTime(), departureTimes.get(i)) > 0)) {
						String stopSequence = String.valueOf(Integer.parseInt(stopSequences.get(i)) + 1);
						stopTime.setStopSequence(stopSequence);
						for (int j = i + 1; j < stopSequences.size(); j++) {
							stopSequences.set(j, String.valueOf(Integer.parseInt(stopSequences.get(j)) + 1));
						}

						tripIDs.add(i + 1, stopTime.getTripID());
						arrivalTimes.add(i + 1, stopTime.getArrivalTime());
						departureTimes.add(i + 1, stopTime.getDepartureTime());
						stopIDs.add(i + 1, stopTime.getStopID());
						stopSequences.add(i + 1, stopTime.getStopSequence());
						stopHeadsigns.add(i + 1, stopTime.getStopHeadsign());
						pickupTypes.add(i + 1, stopTime.getPickupType());
						dropOffTypes.add(i + 1, stopTime.getDropOffType());
					}
				}
			}
			else {
				for (int i = 1; i < stopSequences.size(); i++) {
					if ((Integer.parseInt(stopTime.getStopSequence()) > Integer.parseInt(stopSequences.get(i))) && ((Integer.parseInt(stopTime.getStopSequence()) == Integer.parseInt(stopSequences.get(i+1))))) {
						if ((compareTimes(stopTime.getArrivalTime(), arrivalTimes.get(i)) > 0) && (compareTimes(stopTime.getArrivalTime(), departureTimes.get(i)) > 0)) {
							for (int j = i + 1; j < stopSequences.size(); j++) {
								stopSequences.set(j, String.valueOf(Integer.parseInt(stopSequences.get(j)) + 1));
							}

							tripIDs.add(i + 1, stopTime.getTripID());
							arrivalTimes.add(i + 1, stopTime.getArrivalTime());
							departureTimes.add(i + 1, stopTime.getDepartureTime());
							stopIDs.add(i + 1, stopTime.getStopID());
							stopSequences.add(i + 1, stopTime.getStopSequence());
							stopHeadsigns.add(i + 1, stopTime.getStopHeadsign());
							pickupTypes.add(i + 1, stopTime.getPickupType());
							dropOffTypes.add(i + 1, stopTime.getDropOffType());
						}
					}
				}

			}

			editListOne.setItems(tripIDs);
			editListTwo.setItems(arrivalTimes);
			editListThree.setItems(departureTimes);
			editListFour.setItems(stopIDs);
			editListFive.setItems(stopSequences);
			editListSix.setItems(stopHeadsigns);
			editListSeven.setItems(pickupTypes);
			editListEight.setItems(dropOffTypes);

			StopTimes stopTimes = transitSystem.getStopTimes();
			stopTimes.addStopTime(stopTime);
			stopsOnTrip.addStopTimeOnTrip(stopTime.getTripID(), stopTime);
			transitSystem.setAllStopTimes(stopTimes);

			/*
			ArrayList<StopTime> oldStoptimes = (ArrayList<StopTime>) stopTimeObserver.getStopTimes();
			//StopTimes editStopTimes = transitSystem.getStopTimes();
			for(int i = 1; i < stopSequences.size(); i++) {
				StopTime stopTimeOld = transitSystem.getStopTimes().findStopTimesOnTripWithStopID(currentTripID, stopIDs.get(i));
				StopTime stopTimeNew = stopTimeOld.setStopSequence(stopSequences.get(i));

				transitSystem.getStopTimes().getAllStopTimes().re

				StopTime stopTimeOld = stopTimes.get(editStopTime.get(i) - 1);
				StopTime stopTimeNew = new StopTime(editListOne.getItems().get(editStopTime.get(i)), editListTwo.getItems().get(editStopTime.get(i)), editListThree.getItems().get(editStopTime.get(i)), editListFour.getItems().get(editStopTime.get(i)), editListFive.getItems().get(editStopTime.get(i)), editListSix.getItems().get(editStopTime.get(i)), editListSeven.getItems().get(editStopTime.get(i)), editListEight.getItems().get(editStopTime.get(i)));

				if(handler.stopTimesDataValidate(stopTimeNew.toString().split(","))){
					editStopTimes.deleteStopTime(stopTimeOld);
					editStopTimes.addStopTime(stopTimeNew);

					stopsOnTrip.deleteStopTimeOnTrip(stopTimeOld.getTripID(), stopTimeOld);

					stopsOnTrip.addStopTimeOnTrip(stopTimeNew.getTripID(), stopTimeNew);
				}else{
					alertInvalidEdit();
					validData = false;
				}

			}
			transitSystem.setAllStopTimes(editStopTimes);
			*/

			whichEdit = 3;
			// Cases: 1. times and sequence are present - times do not overlap, stop sequences must be adjusted
			// 		  2. time only present: need to make sure doesn't overlap, give stop sequence, and adjust rest
		}

	}

	private int compareTimes(String time1, String time2) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date1 = format.parse(time1);
			Date date2 = format.parse(time2);
			if (date1.before(date2)) {
				return -1;
			} else if (date1.after(date2)) {
				return 1;
			} else {
				return 0;
			}
		} catch (ParseException ex) {
			return -2;
		}
	}
}