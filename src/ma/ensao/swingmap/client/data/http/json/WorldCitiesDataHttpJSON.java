package ma.ensao.swingmap.client.data.http.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.ezware.dialog.task.TaskDialogs;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import ma.ensao.swingmap.client.data.http.api.WorldCitiesDataAPI;

public class WorldCitiesDataHttpJSON implements WorldCitiesDataAPI {

	@Override
	public List<String> getCountryList(InputStream stream) {
		List<String> countryList = new ArrayList<String>();
		try {
			Gson gson = new Gson();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
	        countryList = gson.fromJson(reader, List.class);
		} catch (UnsupportedEncodingException e) {
			TaskDialogs.showException(e);
		}
		return countryList;
	}

	@Override
	public List<String> getCityList(InputStream stream) {
		List<String> cityList = new ArrayList<String>();
		try {
			Gson gson = new Gson();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			cityList = gson.fromJson(reader, List.class);
		} catch (UnsupportedEncodingException e) {
			TaskDialogs.showException(e);
		}
		return cityList;
	}

	@Override
	public double[] getPosition(InputStream stream) {
		double[] position = null;
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(stream));
			reader.beginObject();
			position = new double[2];
			while (reader.hasNext()) {
				String name = reader.nextName();
				if(name.equals("latitude")) {
					position[0] = reader.nextDouble();			 
				} else if(name.equals("longitude")) {
					position[1] = reader.nextDouble();
				}
			}
			reader.endObject();
			reader.close();
		} catch (IOException e) {
			TaskDialogs.showException(e);
		}
		return position;
	}

}
