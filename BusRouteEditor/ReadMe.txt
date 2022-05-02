Transit Project:

	This project was done to teach software engineers how to use github and how to work as a team. The project had 15 diiferent tasks that each group choose 10 of. This is why you will see some unclompleted methods. They relate to tasks that were not selected by our group.

	When using websites like google maps to find bus routes they actually import this same data. Then map it and display it. This application was mean to simulate that while teaching people how to work in groups and use github. The program read in information from 4 files. Using that information files would be linked (namely the stops to trips and routes to trips). The information was then displayed so a user could edit the information and save it. All information had to be checked and meet certain standards before being saved. 

Files-

Controller (Gabe Woerishofer): The main controller for the GUI. Handle all logic behind the buttons. It also handled passing data too and recieiving data from the observers.

Driver (Gabe Woerishofer): Where the main is stored handled starting the GUI.

InvalidContentLineException (Nick Sharrer): Error extension class to be thrown

InvalidHeaderLinException (Nick Sharrer): Error extension class to be thrown

InvalidIDException (Nick Sharrer): Error extension class to be thrown

Observer (Nick Sharrer): Abstract class for the other observers to implement

Route (Alex Shulta): A single route that stores all the information of 1 line in a routes file.

RouteObserver (Alex Shulta): A class that is made to update the stored information when it has been updated. For routes

Routes (Alex Shulta): A list of all the routes with a search function.

Sample.fxml (Gabe Woerishofer): The saved GUI layout information. It is sizes, colors, and other GUI layout related information.

Stop (Alex Shulta): A single stop that stores all the information from 1 line in the stops file.

StopObserver (Alex Shulta): A class that is made to update the stored information when it has been 
updated. For Stops

Stops (Alex Shulta): A list of all the stops with a search function

StopsOnTrip (Alex Shulta): A Trip can have multiple stops this methods links and stores the links. 

StopTime (Alex Shulta): A single stop time with all the information stored from 1 line of the stop time file.

StopTimeObserver (Reid Witt): A class that is made to update the stored information when it has been updated. For stop times.

StopTimes (Reid Witt): A list of all the stop times with a search function.

Subject (Nick Sharrer): An interface that the Transit System implements. Has 2 required methods for observers.

TransitSystem (Nick Sharrer): Holds the list of the displayed values. For all routes, stops, stop times, and trips. Tells observers when to pull this information and update stored information.

Trip (Nick Sharrer): Stores all the information for a trip which is 1 line in the trip file.

TripObserver (Nick Sharrer): A class that is made to update the stored information when it has been updated. For trips

Trips (Nick Sharrer): A list of the trip file saved with a search function.

TripsOnRoute (Nick Sharrer): A tripe can have multiple routes this links the routes to trips.
