package com.railway.model;

import java.util.ArrayList;

import com.railway.handlers.Algorithms.Algorithms;

public class Train {

	String name = "";
	String source = "";
	String destination = "";
	ArrayList<String> intermediate = new ArrayList<String>();
	String departure = "";
	String arrival = "";
	ArrayList<String> periodicity = new ArrayList<String>();
	Algorithms SeatAlgorithm;
	int ACCompartmentNo = 0;
	int ACCompartmentSeats = 0;
	int ACCompartmentCost = 0;
	int NONACCompartmentNo = 0;
	int NONACCompartmentSeats = 0;
	int NONACCompartmentCost = 0;
	String text = "";

	@Override
	public String toString() {
		return "Train [name=" + name + ", source=" + source + ", destination=" + destination + ", intermediate="
				+ intermediate + ", departure=" + departure + ", arrival=" + arrival + ", periodicity=" + periodicity
				+ ", SeatAlgorithm=" + SeatAlgorithm + ", ACCompartmentNo=" + ACCompartmentNo + ", ACCompartmentSeats="
				+ ACCompartmentSeats + ", ACCompartmentCost=" + ACCompartmentCost + ", NONACCompartmentNo="
				+ NONACCompartmentNo + ", NONACCompartmentSeats=" + NONACCompartmentSeats + ", NONACCompartmentCost="
				+ NONACCompartmentCost + ", text=" + text + ", ID=" + ID + "]";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	int ID = -1;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public ArrayList<String> getIntermediate() {
		return intermediate;
	}

	public void setIntermediate(ArrayList<String> intermediate) {
		this.intermediate = intermediate;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public ArrayList<String> getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(ArrayList<String> periodicity) {
		this.periodicity = periodicity;
	}

	public Algorithms getSeatAlgorithm() {
		return SeatAlgorithm;
	}

	public void setSeatAlgorithm(String seatAlgorithm) {
		String input = seatAlgorithm.toUpperCase();
		if (input.equals("ODD_FIRST")) {
			this.SeatAlgorithm = Algorithms.ODD_FIRST;
		}
		else if (input.equals("EVEN_FIRST")) {
			this.SeatAlgorithm = Algorithms.EVEN_FIRST;
		}
		else if (input.equals("SCATTERRED")) {
			this.SeatAlgorithm = Algorithms.SCATTERRED;
		}
		else if (input.equals("ORDERED")) {
			this.SeatAlgorithm = Algorithms.ORDERED;
		}
		else {
			this.SeatAlgorithm = Algorithms.ORDERED;
		}
	}

	public int getACCompartmentNo() {
		return ACCompartmentNo;
	}

	public void setACCompartmentNo(int aCCompartmentNo) {
		ACCompartmentNo = aCCompartmentNo;
	}

	public int getACCompartmentSeats() {
		return ACCompartmentSeats;
	}

	public void setACCompartmentSeats(int aCCompartmentSeats) {
		ACCompartmentSeats = aCCompartmentSeats;
	}

	public int getACCompartmentCost() {
		return ACCompartmentCost;
	}

	public void setACCompartmentCost(int aCCompartmentCost) {
		ACCompartmentCost = aCCompartmentCost;
	}

	public int getNONACCompartmentNo() {
		return NONACCompartmentNo;
	}

	public void setNONACCompartmentNo(int nONACCompartmentNo) {
		NONACCompartmentNo = nONACCompartmentNo;
	}

	public int getNONACCompartmentSeats() {
		return NONACCompartmentSeats;
	}

	public void setNONACCompartmentSeats(int nONACCompartmentSeats) {
		NONACCompartmentSeats = nONACCompartmentSeats;
	}

	public int getNONACCompartmentCost() {
		return NONACCompartmentCost;
	}

	public void setNONACCompartmentCost(int nONACCompartmentCost) {
		NONACCompartmentCost = nONACCompartmentCost;
	}
}
