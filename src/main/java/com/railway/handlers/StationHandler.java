
package com.railway.handlers;

import com.railway.dao.StationsDAO;
import com.railway.model.Station;

public class StationHandler {
	private final StationsDAO stations = new StationsDAO();

	public Station handleRead(String code) {
		Station station = new Station();
		if (stations.isStationExist(code)) {
			System.out.println("Station Exist");
			station = stations.getStation(code);
			return station;
		}
		station.setMessage("Station Not Found");
		return station;
	}

	public Station handleCreate(String name, String code) {
		Station station = new Station();
		if (stations.isStationExist(code)) {
			station.setMessage("Station already Exists");
			return station;
		}
		stations.createStation(name, code);
		station.setMessage("Station Created Successfully");
		return station;
	}

	public Station handleDelete(String code) {
		Station station = new Station();
		if (!stations.isStationExist(code)) {
			station.setMessage("Station Does not Exist");
			return station;
		}
		stations.deleteStation(code);
		station.setMessage("Deleted Station Successfully");
		return station;
	}

	public Station handleUpdate(String oldCode, String newCode, String newName) {
		Station station = new Station();
		if (!stations.isStationExist(oldCode)) {
			station.setMessage("Station Does not Exist");
			return station;
		}
		stations.updateStation(oldCode, newCode, newName);
		station.setMessage("Station Updated Successfully");
		return station;
	}
}
