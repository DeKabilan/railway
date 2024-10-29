package com.railway.handlers;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.railway.dao.StationsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.model.Train;

public class TrainHandler {
	private final TrainsDAO trains = new TrainsDAO();
	private final StationsDAO stationsdao = new StationsDAO();

	public Train handleRead(String name) {
		Train trainFromDB = trains.getTrain(name);
		if (!trains.isTrainExist(name)) {
			Train train = new Train();
			train.setText("Train Not Found");
			return train;
		}
		return trainFromDB;
	}

	public Train handleDelete(String name) {
		Train train = new Train();
		if (!trains.isTrainExist(name)) {
			train.setText("Train Not Found");
			return train;
		}
		trains.deleteTrain(name);
		train.setText("Train Deleted");
		return train;
	}

	public Train handleCreate(HttpServletRequest request) {
		String name = request.getParameter("tname");

		if (trains.isTrainExist(name)) {
			Train train = new Train();
			train.setText("Train already exists");
			return train;
		}

		Train train = new Train();
		train.setName(name);
		train.setSeatAlgorithm(request.getParameter("tseatalgo"));

		String source = request.getParameter("tsource");
		if (stationsdao.isStationExist(source)) {
			train.setSource(source);
		} else {
			train.setText("Source not found");
			return train;
		}

		String destination = request.getParameter("tdestination");
		if (stationsdao.isStationExist(destination)) {
			train.setDestination(destination);
		} else {
			train.setText("Destination not found");
			return train;
		}

		String strDeparture = request.getParameter("tdeparture");
		int hour = Integer.parseInt(strDeparture.substring(0, 2));
		int minute = Integer.parseInt(strDeparture.substring(2, 4));
		if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
			train.setText("Invalid Time");
			return train;
		}
		train.setDeparture(hour + ":" + minute);

		String strArrival = request.getParameter("tarrival");
		hour = Integer.parseInt(strArrival.substring(0, 2));
		minute = Integer.parseInt(strArrival.substring(2, 4));
		if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
			train.setText("Invalid Time");
			return train;
		}
		train.setArrival(hour + ":" + minute);

		String periodicity = request.getParameter("tperiodicity");
		String[] listPeriod = periodicity.split(",");
		ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listPeriod));
		train.setPeriodicity(itemList);

		String intermediate = request.getParameter("tintermediate");
		String[] listInter = intermediate.split(",");
		itemList = new ArrayList<>(Arrays.asList(listInter));
		train.setIntermediate(itemList);

		Integer ACno = Integer.parseInt(request.getParameter("tacno"));
		Integer ACseats = Integer.parseInt(request.getParameter("tacseats"));
		Integer ACcost = Integer.parseInt(request.getParameter("taccost"));
		Integer NONACno = Integer.parseInt(request.getParameter("tnonacno"));
		Integer NONACseats = Integer.parseInt(request.getParameter("tnonacseats"));
		Integer NONACcost = Integer.parseInt(request.getParameter("tnonaccost"));

		train.setACCompartmentNo(ACno);
		train.setACCompartmentSeats(ACseats);
		train.setACCompartmentCost(ACcost);
		train.setNONACCompartmentNo(NONACno);
		train.setNONACCompartmentSeats(NONACseats);
		train.setNONACCompartmentCost(NONACcost);
		trains.createTrain(train);
		train.setText("Train Created");
		return train;
	}

	public Train handleUpdate(HttpServletRequest request) {
		String oldtname = request.getParameter("oldtname");

		if (!trains.isTrainExist(oldtname)) {
			Train trainFromDB = new Train();
			trainFromDB.setText("Train not Found");
			return trainFromDB;
		}
		Train trainFromDB = trains.getTrain(oldtname);

		String tname = request.getParameter("tname");
		if (tname != null && !tname.isEmpty()) {
			trainFromDB.setName(tname);
		}

		String algorithm = request.getParameter("tseatalgo");
		if (algorithm != null && !algorithm.isEmpty()) {
			trainFromDB.setSeatAlgorithm(algorithm);
		}

		String source = request.getParameter("tsource");
		if (source != null && !source.isEmpty()) {
			if (stationsdao.isStationExist(source)) {
				trainFromDB.setSource(source);
			} else {
				trainFromDB.setText("Source not found");
				return trainFromDB;
			}
		}

		String destination = request.getParameter("tdestination");
		if (destination != null && !destination.isEmpty()) {
			if (stationsdao.isStationExist(destination)) {
				trainFromDB.setDestination(destination);
			} else {
				trainFromDB.setText("Destination not found");
				return trainFromDB;
			}
		}

		String strDeparture = request.getParameter("tdeparture");
		if (strDeparture != null && !strDeparture.isEmpty()) {
			String returnString = strDeparture;
			int hour = Integer.parseInt(strDeparture.substring(0, 2));
			int minute = Integer.parseInt(strDeparture.substring(2, 4));
			if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
				trainFromDB.setDeparture(returnString);
			} else {
				trainFromDB.setText("Invalid Time");
				return trainFromDB;
			}
		}
		String strArrival = request.getParameter("tarrival");
		if (strArrival != null && !strArrival.isEmpty()) {
			int hour = Integer.parseInt(strArrival.substring(0, 2));
			int minute = Integer.parseInt(strArrival.substring(2, 4));
			if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
				trainFromDB.setArrival(strArrival);
			} else {
				trainFromDB.setText("Invalid Time");
				return trainFromDB;
			}
		}
		String acNoStr = request.getParameter("tacno");
		if (acNoStr != null && !acNoStr.isEmpty()) {
			Integer acNo = Integer.parseInt(acNoStr);
			trainFromDB.setACCompartmentNo(acNo);
		}

		String acSeatsStr = request.getParameter("tacseats");
		if (acSeatsStr != null && !acSeatsStr.isEmpty()) {
			Integer acSeats = Integer.parseInt(acSeatsStr);
			trainFromDB.setACCompartmentSeats(acSeats);
		}

		String acCostStr = request.getParameter("taccost");
		if (acCostStr != null && !acCostStr.isEmpty()) {
			Integer acCost = Integer.parseInt(acCostStr);
			trainFromDB.setACCompartmentCost(acCost);
		}
		String nonAcNoStr = request.getParameter("tnonacno");
		if (nonAcNoStr != null && !nonAcNoStr.isEmpty()) {
			Integer nonAcNo = Integer.parseInt(nonAcNoStr);
			trainFromDB.setNONACCompartmentNo(nonAcNo);
		}
		String nonAcSeatsStr = request.getParameter("tnonacseats");
		if (nonAcSeatsStr != null && !nonAcSeatsStr.isEmpty()) {
			Integer nonAcSeats = Integer.parseInt(nonAcSeatsStr);
			trainFromDB.setNONACCompartmentSeats(nonAcSeats);
		}
		String nonAcCostStr = request.getParameter("tnonaccost");
		if (nonAcCostStr != null && !nonAcCostStr.isEmpty()) {
			Integer nonAcCost = Integer.parseInt(nonAcCostStr);
			trainFromDB.setNONACCompartmentCost(nonAcCost);
		}
		String periodicity = request.getParameter("tperiodicity");
		if (periodicity != null && !periodicity.isEmpty()) {
			String[] listPeriod = periodicity.split(",");
			ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listPeriod));
			trainFromDB.setPeriodicity(itemList);
		}
		String intermediate = request.getParameter("tintermediate");
		if (intermediate != null && !intermediate.isEmpty()) {
			String[] listInter = intermediate.split(",");
			ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listInter));
			trainFromDB.setIntermediate(itemList);
		}

		System.out.println(trainFromDB);
		trainFromDB.setText("Train Updated Successfully");
		trains.deleteTrain(oldtname);
		trains.createTrain(trainFromDB);
		return trainFromDB;
	}
}
