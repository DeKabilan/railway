package com.railway.utils;

import java.util.ArrayList;

import com.railway.model.Train;

public class Filters {

	public ArrayList<Train> additionalFilters(ArrayList<Train> currentArray, String departure, String arrival) {
		ArrayList<Train> newArray = new ArrayList<Train>();
		if ((departure == null || departure == "") && (arrival == null || arrival == "")) {
			return currentArray;
		}
		if (departure != "" || departure != null) {
			for (Train train : currentArray) {
				switch (departure) {
				case ("Morning"):
					if (Integer.parseInt(train.getDeparture()) >= 0600
							&& Integer.parseInt(train.getDeparture()) < 1200) {
						newArray.add(train);
					}
					break;
				case ("Afternoon"):
					if (Integer.parseInt(train.getDeparture()) >= 1200
							&& Integer.parseInt(train.getDeparture()) < 1800) {
						newArray.add(train);
					}
					break;
				case ("Evening"):
					if (Integer.parseInt(train.getDeparture()) >= 1800
							&& Integer.parseInt(train.getDeparture()) < 2400) {
						newArray.add(train);
					}
					break;
				case ("Night"):
					if (Integer.parseInt(train.getDeparture()) >= 0000
							&& Integer.parseInt(train.getDeparture()) < 0600) {
						newArray.add(train);
					}
					break;
				default:
					break;
				}

			}
			currentArray = newArray;
			System.out.println("current array: "+ currentArray);
		}
		if (arrival != "" || arrival != null) {
			newArray = new ArrayList<Train>();
			for (Train train : currentArray) {
				switch (arrival) {
				case ("Morning"):
					if (Integer.parseInt(train.getArrival()) >= 0600 && Integer.parseInt(train.getArrival()) < 1200) {
						newArray.add(train);
					}
					break;
				case ("Afternoon"):
					if (Integer.parseInt(train.getArrival()) >= 1200 && Integer.parseInt(train.getArrival()) < 1800) {
						newArray.add(train);
					}
					break;
				case ("Evening"):
					if (Integer.parseInt(train.getArrival()) >= 1800 && Integer.parseInt(train.getArrival()) < 2400) {
						newArray.add(train);
					}
					break;
				case ("Night"):
					if (Integer.parseInt(train.getArrival()) >= 0000 && Integer.parseInt(train.getArrival()) < 0600) {
						newArray.add(train);
					}
					break;
				default:
					break;
				}

			}
		}
		System.out.println("new array: "+ newArray);
		return newArray;
	}
}
