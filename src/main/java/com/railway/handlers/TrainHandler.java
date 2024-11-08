package com.railway.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.railway.dao.StationsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;

public class TrainHandler {
	private final TrainsDAO trains = new TrainsDAO();
	private final StationsDAO stationsdao = new StationsDAO();
//	private static Logger logger = Logger.getLogger(TrainHandler.class.getName());

	public Train handleRead(String name) {

		Train train = new Train();
		;
		try {
			if (!trains.isTrainExist(name)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
			}
			return trains.getTrain(name);
		} catch (CustomExceptions ce) {
//			logger.log(Level.SEVERE, "exception occurred while reading train details {0}, {1}", new Object[] {});
			train.setText(ce.getException().getMessage());
			return train;
		}
	}

	public Train handleDelete(String name) {

		Train train = new Train();
		try {

			if (!trains.isTrainExist(name)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
			}
			trains.deleteTrain(name);
			train.setText("Train Deleted");
			return train;
		} catch (CustomExceptions ce) {
			train.setText(ce.getException().getMessage());
			return train;

		}
	}

	public Train handleCreate(String name, String tseatalgo, String source, String destination, String strDeparture,
			String strArrival, String periodicity, String intermediate, Integer ACno, Integer ACseats, Integer ACcost,
			Integer NONACno, Integer NONACseats, Integer NONACcost) {

		Train train = new Train();
		try {

			if (trains.isTrainExist(name)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_ALREADY_EXISTS);
			}
			
			train.setName(name);
			train.setSeatAlgorithm(tseatalgo);
			
			if (stationsdao.isStationExist(source)) {
				train.setSource(source);
			} else {
				throw new CustomExceptions(CustomExceptions.Exceptions.SOURCE_DOESNT_EXIST);
			}
			
			if (stationsdao.isStationExist(destination)) {
				train.setDestination(destination);
			} else {
				throw new CustomExceptions(CustomExceptions.Exceptions.DESTINATION_DOESNT_EXIST);
			}
			

			train.setDeparture(strDeparture);
			train.setArrival(strArrival);
			
			String[] listPeriod = periodicity.split(",");
			ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listPeriod));
			train.setPeriodicity(itemList);
			
			String[] listInter = intermediate.split(",");
			itemList = new ArrayList<>(Arrays.asList(listInter));
			train.setIntermediate(itemList);
			train.setACCompartmentNo(ACno);
			train.setACCompartmentSeats(ACseats);
			train.setACCompartmentCost(ACcost);
			train.setNONACCompartmentNo(NONACno);
			train.setNONACCompartmentSeats(NONACseats);
			train.setNONACCompartmentCost(NONACcost);
			trains.createTrain(train);
			train.setText("Train Created");
			return train;
		} catch (CustomExceptions ce) {
			train.setText(ce.getException().getMessage());
			return train;
		}
	}

	
	public Train handleUpdate(String oldtname, String tname, String algorithm, String source, String destination, String strDeparture,
			String strArrival, String periodicity, String intermediate, String acNoStr, String acSeatsStr, String acCostStr,
			String nonAcNoStr, String nonAcSeatsStr, String nonAcCostStr) {
		Train trainFromDB = new Train();
		try {
			if (!trains.isTrainExist(oldtname)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
			}
			trainFromDB = trains.getTrain(oldtname);

			if (tname != null && !tname.isEmpty()) {
				trainFromDB.setName(tname);
			}

			if (algorithm != null && !algorithm.isEmpty()) {
				trainFromDB.setSeatAlgorithm(algorithm);
			}

			if (source != null && !source.isEmpty()) {
				if (stationsdao.isStationExist(source)) {
					trainFromDB.setSource(source);
				} else {
					throw new CustomExceptions(CustomExceptions.Exceptions.SOURCE_DOESNT_EXIST);
				}
			}

			if (destination != null && !destination.isEmpty()) {
				if (stationsdao.isStationExist(destination)) {
					trainFromDB.setDestination(destination);
				} else {
					throw new CustomExceptions(CustomExceptions.Exceptions.DESTINATION_DOESNT_EXIST);
				}
			}

			if (strDeparture != null && !strDeparture.isEmpty()) {
				trainFromDB.setDeparture(strDeparture);
			}
			if (strArrival != null && !strArrival.isEmpty()) {
				trainFromDB.setArrival(strArrival);

			}
			if (acNoStr != null && !acNoStr.isEmpty()) {
				Integer acNo = Integer.parseInt(acNoStr);
				trainFromDB.setACCompartmentNo(acNo);
			}

			if (acSeatsStr != null && !acSeatsStr.isEmpty()) {
				Integer acSeats = Integer.parseInt(acSeatsStr);
				trainFromDB.setACCompartmentSeats(acSeats);
			}

			if (acCostStr != null && !acCostStr.isEmpty()) {
				Integer acCost = Integer.parseInt(acCostStr);
				trainFromDB.setACCompartmentCost(acCost);
			}
			if (nonAcNoStr != null && !nonAcNoStr.isEmpty()) {
				Integer nonAcNo = Integer.parseInt(nonAcNoStr);
				trainFromDB.setNONACCompartmentNo(nonAcNo);
			}
			if (nonAcSeatsStr != null && !nonAcSeatsStr.isEmpty()) {
				Integer nonAcSeats = Integer.parseInt(nonAcSeatsStr);
				trainFromDB.setNONACCompartmentSeats(nonAcSeats);
			}
			if (nonAcCostStr != null && !nonAcCostStr.isEmpty()) {
				Integer nonAcCost = Integer.parseInt(nonAcCostStr);
				trainFromDB.setNONACCompartmentCost(nonAcCost);
			}
			if (periodicity != null && !periodicity.isEmpty()) {
				String[] listPeriod = periodicity.split(",");
				ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listPeriod));
				trainFromDB.setPeriodicity(itemList);
			}
			if (intermediate != null && !intermediate.isEmpty()) {
				String[] listInter = intermediate.split(",");
				ArrayList<String> itemList = new ArrayList<>(Arrays.asList(listInter));
				trainFromDB.setIntermediate(itemList);
			}

			trainFromDB.setText("Train Updated Successfully");
			trains.deleteTrain(oldtname);
			trains.createTrain(trainFromDB);
			return trainFromDB;
		}
		catch(CustomExceptions ce) {
			trainFromDB.setText(ce.getException().getMessage());
			return trainFromDB;
		}
	
	}
}
