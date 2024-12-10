
package com.railway.handlers;

import com.railway.dao.StationsDAO;
import com.railway.model.Station;
import com.railway.utils.CustomExceptions;

public class StationHandler {
	private final StationsDAO stations = new StationsDAO();

	public Station handleRead(String code) {
		Station station = new Station();
		try {
			if (stations.isStationExist(code)) {
				station = stations.getStation(code);
				return station;
			}
			throw new CustomExceptions(CustomExceptions.Exceptions.STATION_DOESNT_EXIST);
		} catch (CustomExceptions ce) {
			station.setMessage(ce.getException().getMessage());
			return station;
		}
	}

	public Station handleCreate(String name, String code) {
		Station station = new Station();
		try {
			if (stations.isStationExist(code) || stations.isStationExist(name)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.STATION_ALREADY_EXISTS);
			}
			stations.createStation(name, code);
			station.setMessage("Station Created Successfully");
			return station;
		} catch (CustomExceptions ce) {
			station.setMessage(ce.getException().getMessage());
			return station;
		}

	}

	public Station handleDelete(String code) {
		Station station = new Station();
		try {

			if (!stations.isStationExist(code)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.STATION_DOESNT_EXIST);
			}
			stations.deleteStation(code);
			station.setMessage("Deleted Station Successfully");
			return station;
		} catch (CustomExceptions ce) {
			station.setMessage(ce.getException().getMessage());
			return station;
		}
	}

	public Station handleUpdate(String oldCode, String newCode, String newName){
		
		Station station = new Station();
		try {
 
			if (!stations.isStationExist(oldCode)) {
				throw new CustomExceptions(CustomExceptions.Exceptions.STATION_DOESNT_EXIST);
			}
			Station oldStation = stations.getStation(oldCode);
			if(newCode!=null && !newCode.isEmpty()) {
				oldStation.setCode(newCode);
			}
			if(newName!=null && !newName.isEmpty()) {
				oldStation.setName(newName);
			}
			if(newCode.length()!=3) {
				throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
			}
			stations.updateStation(oldCode, oldStation.getCode(), oldStation.getName());
			station.setMessage("Station Updated Successfully");
			return station;
		} catch (CustomExceptions ce) {
			station.setMessage(ce.getException().getMessage());
			return station;
		}
	}
}
