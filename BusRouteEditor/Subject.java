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
public interface Subject {

	/**
	 * Attach an Observer to the list of Observers that will be notified
	 * @param observer Observer to be notified of changes to the Subject
	 */
	void attach(Observer observer);

	/**
	 * Lets the Observers know that the data of the subject has changed
	 */
	void notifyObservers();
}