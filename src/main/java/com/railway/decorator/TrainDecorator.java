package com.railway.decorator;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.railway.model.Station;
import com.railway.model.Train;

public class TrainDecorator {
	public JSONObject decorate(ArrayList<Train> trainList) {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		for(Train train: trainList) {
			JSONObject trainObj = new JSONObject();
			JSONObject routes = new JSONObject();
			JSONArray intermediate = new JSONArray();
			JSONArray periodicity = new JSONArray();
			JSONObject compartments = new JSONObject();
			JSONArray compartmentDetail = new JSONArray();
			if(train.getACCompartmentNo()!=0) {
				JSONObject AC = new JSONObject();
				AC.put("COMPARTMENT_TYPE", "AC");
				AC.put("NO_OF_SEATS", train.getACCompartmentSeats());
				AC.put("NO_OF_COMPARTMENTS", train.getACCompartmentNo());
				AC.put("COST_PER_TICKET", train.getACCompartmentCost());
				compartmentDetail.add(AC);
			}
			if(train.getACCompartmentNo()!=0) {
				JSONObject NONAC = new JSONObject();
				NONAC.put("COMPARTMENT_TYPE", "NONAC");
				NONAC.put("NO_OF_SEATS", train.getNONACCompartmentSeats());
				NONAC.put("NO_OF_COMPARTMENTS", train.getNONACCompartmentNo());
				NONAC.put("COST_PER_TICKET", train.getNONACCompartmentCost());
				compartmentDetail.add(NONAC);
			}
			compartments.put("TOTAL_NO_OF_COMPARTMENTS", train.getACCompartmentNo()+train.getNONACCompartmentNo());
			compartments.put("DETAIL", compartmentDetail);
			routes.put("SOURCE", train.getSource());
			routes.put("DESTINATION", train.getDestination());
			for(String stops:train.getIntermediate()) {
				intermediate.add(stops);
			}
			routes.put("INTERMIDATE", intermediate);
			routes.put("DEPARTURE", train.getDeparture());
			routes.put("ARRIVAL", train.getArrival());
			for(String days : train.getPeriodicity()) {
				periodicity.add(days);
			}
			routes.put("PERIODICITY", periodicity);
			routes.put("SEAT_ALLOCATION_ALGORITHM",train.getSeatAlgorithm().toString());
			routes.put("COMPARTMENTS", compartments);
			trainObj.put("TRAIN_NAME", train.getName());
			trainObj.put("ROUTES", routes);
			data.add(trainObj);
			result.put("DATA", data);
		}
		
		return result;
		
	}
}
