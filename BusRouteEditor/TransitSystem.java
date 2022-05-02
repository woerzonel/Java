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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Nick Scharrer (scharrernf@msoe.edu)
 * @version 1.0
 * @created 12-Apr-2018 10:33:10 AM
 */

public class TransitSystem implements Subject {

	private Routes allRoutes;
	private Stops allStops;
	private StopTimes allStopTimes;
	private Trips allTrips;
	private List<Observer> observers;

    /**
     * Construct a TransitSystem with the populated data
	 *
     * @param allRoutes populated Routes object
     * @param allStops populated Stops object
     * @param allStopTimes populated StopTimes object
     * @param allTrips populated Trips object
     */
	public TransitSystem(Routes allRoutes, Stops allStops, StopTimes allStopTimes, Trips allTrips){
		this.allRoutes = allRoutes;
		this.allStops = allStops;
		this.allStopTimes = allStopTimes;
		this.allTrips = allTrips;
		observers = new ArrayList<>();
	}

	/**
	 * Attach an observer to the Subject and update it with the current data
	 * @param observer
	 */
	public void attach(Observer observer){
		observers.add(observer);
		observer.update(this);
	}

	/**
	 * Remove the observer from the Subject so that it is no longer notified and updated
	 * @param observer
	 */
	public void detach(Observer observer){
		observers.remove(observer);
	}

    /**
     * Update all of the attached Observers
     */
	public void notifyObservers(){
		for(Observer observer : observers) {
			observer.update(this);
		}
	}

	public Collection<Route> getRouteCollection() {
		return allRoutes.getAllRoutes();
	}

	public Collection<Trip> getTripCollection() {
		return allTrips.getAllTrips();
	}

	public Collection<Stop> getStopCollection() {
		return allStops.getAllStops();
	}

	public Collection<StopTime> getStopTimeCollection() {
		return allStopTimes.getAllStopTimes();
	}

	public Routes getRoutes() {
	    return allRoutes;
    }

    public Trips getTrips() {
	    return allTrips;
    }

    public Stops getStops() {
	    return allStops;
    }

    public StopTimes getStopTimes() {
        return allStopTimes;
    }

    public void setAllRoutes(Routes allRoutes) {
        this.allRoutes = allRoutes;
        notifyObservers();
    }

    public void setAllStops(Stops allStops) {
        this.allStops = allStops;
        notifyObservers();
    }

    public void setAllStopTimes(StopTimes allStopTimes) {
        this.allStopTimes = allStopTimes;
        notifyObservers();
    }

    public void setAllTrips(Trips allTrips) {
        this.allTrips = allTrips;
        notifyObservers();
    }
}