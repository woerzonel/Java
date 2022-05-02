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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Program to import, display, and edit files with the GTFS format
 * @author woerishofergw
 * @version 1.0
 * @created 12-Apr-2018 10:33:09 AM
 */
public class Driver extends Application {

	public Driver() {

	}

	/**
	 * Starts the application and displays the GUI
     * @param primaryStage main Stage of the application
     * @throws Exception throws any exception that occurs
     */
	@Override
	public void start(Stage primaryStage) throws Exception{
		Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
		Scene scene = new Scene(root, 1400, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Bus stop manager");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Finalize
	 * @throws Throwable
	 */
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Launches the application
	 *
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}

}