/**
 * Brian Rosenblum, Joe Falcone, Scott Howard
 * TSP Nearest Neighbor
 * DAA - Fall 2014
 */

package neighbor;

import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

/**
 * TSP1 Class This class calculates the best tour cost using the nearest
 * neighbor approach to solve a TSP problem.
 *
 */
public class TSP1 {
	static Scanner scanner; // scanner to read a file
	static Scanner fileName; // user input for file names
	static String inFile; // String for the input file
	static String OFile; // String for output file
	static File outFile; // File for output
	static double currentCost = 0; // cost of the current tour
	static double lowestCost = Integer.MAX_VALUE; // the shortest tour cost
	
	// ArrayList of Cities
	static ArrayList<Cities> cityMap = new ArrayList<Cities>();
	static ArrayList<Cities> cityMapClone = new ArrayList<Cities>();
	static ArrayList<Integer> currentRoute = new ArrayList<Integer>();
	
	// Route with lowest cost
	static ArrayList<Integer> lowestRoute = new ArrayList<Integer>();
	static double[][] cityDist;	//2d array of distances

	/**
	 * MAIN
	 * @param args
	 */
	public static void main(String[] args) {
		setFileIONames();
		fillCities();
		neighborHelper();
		printToFile();
	} // end main

	/**
	 * setFileIONames
	 * This method gets the names of the files that the user
	 * desires to use in the execution of the program. This was made a separate
	 * method so as not to clutter other methods with I/O concerns, and to
	 * increase modularity
	 */
	public static void setFileIONames() {
		try {
			fileName = new Scanner(System.in);
			System.out.println("Please enter input file name: ");
			inFile = fileName.next();
			scanner = new Scanner(new File(inFile));
			System.out.println("Please enter output file name: ");
			OFile = fileName.next();
			outFile = new File(OFile); // output file to be printed
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file name. \n\n");
			// Prompt the user to re-enter a file name instead of
			// simply terminating due to an invalid entry
			setFileIONames();
		} // end try catch
	} // end setFileIONames

	/*
	 * fillCities method Reads in the input file and creates a City for each ID
	 * and set of coordinates specified in the input file
	 */
	public static void fillCities() {
		// Proceed to where the first ID, X and Y coordinates are in the file
		for (int i = 0; i < 11; i++) {
			scanner.next();
		} // end for
		while (scanner.hasNextDouble()) {
			String a = scanner.next(); // for the city ID
			String b = scanner.next(); // for the X coordinate
			String c = scanner.next(); // for the Y coordinate
			// convert the Strings above to Integers in order
			// to apply them to a new City object
			Integer id = Integer.valueOf(a);
			Integer x = Integer.valueOf(b);
			Integer y = Integer.valueOf(c);
			// add the City to the list
			cityMap.add(new Cities(id, x, y));
		} // end while
	} // end fillCities

	/**
	 * distCalc method This method calculates the distances between all vertices
	 * and stores them in a 2D array
	 */
	public static void distCalc() {
		cityDist = new double[cityMapClone.size()][cityMapClone.size()];	//new 2d array
		for (int i = 0; i < cityMapClone.size(); i++) {	//for loop to fill each row of 2d array
			for (int j = 0; j < cityMapClone.size(); j++) {	//nested for loop to fill each element in specified row
				if (i == j) {	//if same city
					cityDist[i][j] = 0;	//set same city to distance of 0
				} else {
					cityDist[i][j] = Math.sqrt(Math.pow((cityMapClone.get(j)	//fills 2d array using distance formula
							.getX() - cityMapClone.get(i).getX()), 2)
							+ Math.pow(
									(cityMapClone.get(j).getY() - cityMapClone
											.get(i).getY()), 2));
				} // end else
			} // end for
		} // end for
	} // end distCalc

	/**
	 * shiftLeft method
	 * This method shifts the array list to the left
	 * to ensure that the specified start city is
	 * at index 0
	 * @param start
	 */
	public static void shiftLeft(int start) {
		for (Cities c : cityMap) {	//for every city in list
			cityMapClone.add(c);	//add city to new list
		} // end for

		if (start != 0) {	//if starting city is not at index 0
			while (start != 0) {	//while starting city is not at index 0
				Cities first = cityMapClone.get(0);	//get first element in list and set it to first
				for (int i = 0; i <= cityMap.size() - 2; i++) {	//for loop to ensure proper amount of shifts
					cityMapClone.set(i, cityMapClone.get(i + 1));	//sets the value at a given index to its following value
				} // end for
				
				cityMapClone.set(cityMapClone.size() - 1, first);	//adds the first element in the original list to new shifted list
				start--;	//decreases index of start city
			} // end while
		} // end if
	} // end shiftLeft

	/**
	 * nearestNeighbor method
	 * This method receives a start city and determines the
	 * next closest city using the nearest neighbor algorithm
	 * @param start
	 */
	public static void nearestNeighbor(int start) {
		double lowestVal = Integer.MAX_VALUE;	//lowest value from current city to any other city
		double temp = 0;	//current distance from one city to another
		int tempCityID = 0;	//ID of the next city we are going to
		int nextCity = 0;	//index of the next city we are going to

		shiftLeft(start);
		distCalc();
		int count = 0;		//counter for outer for loop below

		currentRoute.add(cityMapClone.get(0).getID());	//adds start city in index 0 to our current route being explored

		for (int i = 0; count < cityMap.size() - 1; i = nextCity) {	//for loop to incorporate a new "start" city
			for (int j = 0; j < cityMap.size(); j++) {	//for loop to check every city from "start" city
				if (i != j) {	// if not the same city
					temp = cityDist[i][j];	//get distance between cities
					if (temp == lowestVal) {	//if current distance equals our lowest distance observed
						if (cityMapClone.get(j).getID() < tempCityID) {	//if ID of current distance is less than ID of lowest distance
							if (!currentRoute.contains(cityMapClone.get(j)	//if ID is not in our current route explored
									.getID())) {
								tempCityID = cityMapClone.get(j).getID();	//a new next city ID
								nextCity = j;	//a new next city index
								currentRoute.set(currentRoute.size() - 1,	//replace last city ID added to list with new city ID
										tempCityID);
							} // end if
						} // end if

					} // end if
					if (temp < lowestVal) {	//if current distance is less than lowest distance observed
						if (!currentRoute.contains(cityMapClone.get(j).getID())) {	//if ID is not in our current route explored
							lowestVal = temp;	//lowest distance observed is now our current distance
							tempCityID = cityMapClone.get(j).getID();	//a new next city ID
							nextCity = j;	//a new next city index
							if (currentRoute.size() == count + 2) {	//if we already added a city ID to our current route for this loop
								currentRoute.set(currentRoute.size() - 1,	//place last city ID added to list with new city ID
										cityMapClone.get(j).getID());
							} else {
								currentRoute.add(cityMapClone.get(j).getID());	//add new city ID to list
							} // end if
						} // end if
					} // end if
				} // end if
			} // end for
			currentCost = currentCost + lowestVal;	//add lowest distance observed to current cost of our current route being explored
			lowestVal = Integer.MAX_VALUE;	//reset lowest distance observed to largest integer possible
			count++;	//increase count
		} // end for
		currentCost = currentCost + cityDist[0][nextCity];	//add distance from last city visited to start city to our current cost for current route
		currentRoute.add(cityMapClone.get(0).getID());	//add start city ID to our current route explored
		cityMapClone.clear();	//clear our copy of original list passed
	} // end nearestNeighbor

	/**
	 * neighborHelper method
	 * This method cycles through all cities
	 * and calls nearest neighbor using each city as
	 * the starting city.
	 */
	public static void neighborHelper() {
		for (int i = 0; i < cityMap.size(); i++) {	//for loop to make every city a starting city
			nearestNeighbor(i);

			if (currentCost < lowestCost) {	//if current cost of current route explored is less then lowest cost of some other route
				lowestCost = currentCost;	//current cost of current route explored is now lowest cost
				lowestRoute.clear();		//clear the last lowest costing route
				for (int j : currentRoute) {	//for every index in current route
					lowestRoute.add(j);			//add value at index j of current route to lowest route
				} // end for
			} // end if
			currentCost = 0;		//reset current cost
			currentRoute.clear();	//clear current route explored

		} // end for
	} // end neighborHelper

	/**
	 * printToFile method
	 * This method is responsible for generating the
	 * output file to see the results of execution.
	 */
	public static void printToFile() {
		try {
			PrintWriter pr = new PrintWriter(outFile);
			DecimalFormat df = new DecimalFormat("#.##"); // set decimal to two

			// places
			pr.println("NAME: " + outFile.getName());
			pr.println("TYPE: TOUR");
			pr.println("DIMENSION: " + cityMap.size());
			pr.println("COMMENT: best tour cost = " + df.format(lowestCost));
			pr.println("TOUR_SECTION \n");
			for (int i = 0; i < cityMap.size(); i++) {
				pr.println(lowestRoute.get(i));
			} // end for
				// print the last city
			pr.println(lowestRoute.get(0) * -1);
			pr.close(); // close the PrintWriter
		} catch (FileNotFoundException e) {
			System.out.println("Invalid entry for output file name!");
		} // end try catch
	} // end printToFile
} // end class TSP1
