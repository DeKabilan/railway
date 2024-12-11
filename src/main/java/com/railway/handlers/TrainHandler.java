package com.railway.handlers;

import com.railway.dao.StationsDAO;
import com.railway.dao.TrainsDAO;
import com.railway.model.Train;
import com.railway.utils.CustomExceptions;

public class TrainHandler {
	private final TrainsDAO trains = new TrainsDAO();
	private final StationsDAO stationsdao = new StationsDAO();

	public Train handleRead(String name) {
		Train train = new Train();
		try {
			if (!trains.isTrainExist(name)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
			}
			return trains.getTrain(name);
		} catch (CustomExceptions ce) {
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

	public void handleCreate(Train train) throws CustomExceptions{
			if(train.getName()==null || train.getSource()==null || train.getDestination()==null || 
					train.getDeparture()==null || train.getArrival()==null || train.getSeatAlgorithm()==null) {
				throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
			}
			if (trains.isTrainExist(train.getName())) {
				throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_ALREADY_EXISTS);
			}

			if (!stationsdao.isStationExist(train.getSource())) {
				throw new CustomExceptions(CustomExceptions.Exceptions.SOURCE_DOESNT_EXIST);
			}

			if (!stationsdao.isStationExist(train.getDestination())) {
				throw new CustomExceptions(CustomExceptions.Exceptions.DESTINATION_DOESNT_EXIST);
			}

			if(train.getPeriodicity().size()==0) {
				throw new CustomExceptions(CustomExceptions.Exceptions.PERIODICITY_IS_EMPTY);
			}

			for(String stops : train.getIntermediate()) {
				if(!stationsdao.isStationExist(stops)) {
					throw new CustomExceptions(CustomExceptions.Exceptions.STOP_DOESNT_EXIST);
				}
			}
			trains.createTrain(train);	
	}

	public void handleUpdate(String oldtname, Train train) throws CustomExceptions {
		if(oldtname==null) {
			throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
		}
		Train trainFromDB = new Train();
			if (!trains.isTrainExist(oldtname)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.TRAIN_DOESNT_EXIST);
			}
			trainFromDB = trains.getTrain(oldtname);

			if (train.getName() != null && !train.getName().isEmpty()) {
				trainFromDB.setName(train.getName());
			}

			if (train.getSeatAlgorithm()!=null) {
				trainFromDB.setSeatAlgorithm(train.getSeatAlgorithm().name());
			}

			if (train.getSource() != null && !train.getSource().isEmpty()) {
				if (stationsdao.isStationExist(train.getSource())) {
					trainFromDB.setSource(train.getSource());
				} else {
					throw new CustomExceptions(CustomExceptions.Exceptions.SOURCE_DOESNT_EXIST);
				}
			}

			if (train.getDestination() != null && !train.getDestination().isEmpty()) {
				if (stationsdao.isStationExist(train.getDestination())) {
					trainFromDB.setDestination(train.getDestination());
				} else {
					throw new CustomExceptions(CustomExceptions.Exceptions.DESTINATION_DOESNT_EXIST);
				}
			}

			if (train.getDeparture() != null && !train.getDeparture().isEmpty()) {
				trainFromDB.setDeparture(train.getDeparture());
			}
			if (train.getArrival()!= null && !train.getArrival().isEmpty()) {
				trainFromDB.setArrival(train.getArrival());

			}
			if (train.getACCompartmentNo() != -1) {
				trainFromDB.setACCompartmentNo(train.getACCompartmentNo());
			}

			if (train.getACCompartmentSeats()!=-1) {
				trainFromDB.setACCompartmentSeats(train.getACCompartmentSeats());
			}

			if (train.getACCompartmentCost() != -1) {
				trainFromDB.setACCompartmentCost(train.getACCompartmentCost());
			}
			if (train.getNONACCompartmentNo() != -1) {
				trainFromDB.setNONACCompartmentNo(train.getNONACCompartmentNo());
			}

			if (train.getNONACCompartmentSeats()!=-1) {
				trainFromDB.setNONACCompartmentSeats(train.getNONACCompartmentSeats());
			}

			if (train.getNONACCompartmentCost() != -1) {
				trainFromDB.setNONACCompartmentCost(train.getNONACCompartmentCost());
			}
			if (train.getPeriodicity().size()!=0) {
				trainFromDB.setPeriodicity(train.getPeriodicity());
			}
			
			if (train.getIntermediate().size() != 0) {
				for(String stops : train.getIntermediate()) {
					if(!stationsdao.isStationExist(stops)) {
						throw new CustomExceptions(CustomExceptions.Exceptions.STOP_DOESNT_EXIST);
					}
				}
				trainFromDB.setIntermediate(train.getIntermediate());
			}

			trainFromDB.setText("Train Updated Successfully");
			trains.deleteTrain(oldtname);
			trains.createTrain(trainFromDB);

	}
}
