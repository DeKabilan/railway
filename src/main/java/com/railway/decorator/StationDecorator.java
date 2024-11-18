package com.railway.decorator;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.railway.model.Station;

public class StationDecorator {
	public JSONObject decorate(ArrayList<Station> stationList) {
		JSONArray jsonArr = new JSONArray();
		JSONObject result = new JSONObject();
		
		for(Station station: stationList) {
			JSONObject json = new JSONObject();
			json.put("name",station.getName());
			json.put("code",station.getCode());
			jsonArr.add(json);
		}
		result.put("DATA",jsonArr);
		
		return result;
		
	}
}
