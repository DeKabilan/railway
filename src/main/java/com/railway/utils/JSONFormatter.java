package com.railway.utils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.railway.model.Train;

public class JSONFormatter {
	public JSONArray stationstoArray(String path) {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(path)) {
			Object obj = jsonParser.parse(reader);
			JSONArray json = (JSONArray) obj;
			return json;
		}

		catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public List<Train> trainsToArray(String filePath) {
		List<Train> trainList = new ArrayList<>();
		JSONParser parser = new JSONParser();

		try (FileReader reader = new FileReader(filePath)) {
			JSONArray trainsArray = (JSONArray) parser.parse(reader);

			for (Object obj : trainsArray) {
				Train train = new Train();
				JSONObject eachTrain = (JSONObject) obj;
				JSONObject route = (JSONObject) eachTrain.get("ROUTE");
				JSONObject compartments = (JSONObject) route.get("COMPARTMENTS");
				JSONArray compartmentsdata = (JSONArray) compartments.get("DETAILS");
				JSONArray inter = (JSONArray) route.get("INTERMEDIATE");
				train.setName((String) eachTrain.get("TRAIN_NAME"));
				train.setArrival((String) route.get("ARRIVAL"));
				train.setDeparture((String) route.get("DEPARTURE"));
				train.setSource((String) route.get("SOURCE"));
				train.setDestination((String) route.get("DESTINATION"));
				train.setSeatAlgorithm((String) route.get("SEAT_ALLOCATION_ALGORITHM"));
				ArrayList<String> intermediate = new ArrayList<String>();
				for (Object stops : inter) {
					intermediate.add((String) stops);
				}
				train.setIntermediate(intermediate);

				JSONArray period = (JSONArray) route.get("PERIODICITY");
				ArrayList<String> periodicity = new ArrayList<String>();
				for (Object days : period) {
					periodicity.add((String) days);
				}
				train.setPeriodicity(periodicity);
				for (Object types : compartmentsdata) {
					JSONObject comp = (JSONObject) types;
					if (((String) comp.get("COMPARTMENT_TYPE")).equals("AC")) {
						train.setACCompartmentCost(((Long) comp.get("COST_PER_TICKET")).intValue());
						train.setACCompartmentSeats(((Long) comp.get("NO_OF_SEATS")).intValue());
						train.setACCompartmentNo(((Long) comp.get("NO_OF_COMPARTMENTS")).intValue());

					} else if (((String) comp.get("COMPARTMENT_TYPE")).equals("NON-AC")) {
						train.setNONACCompartmentCost(((Long) comp.get("COST_PER_TICKET")).intValue());
						train.setNONACCompartmentSeats(((Long) comp.get("NO_OF_SEATS")).intValue());
						train.setNONACCompartmentNo(((Long) comp.get("NO_OF_COMPARTMENTS")).intValue());
					}

				}
				trainList.add(train);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return trainList;
	}
}
