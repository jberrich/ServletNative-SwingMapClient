package ma.ensao.swingmap.client.data.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ma.ensao.swingmap.client.data.http.api.WorldCitiesDataAPI;
import ma.ensao.swingmap.client.data.http.json.WorldCitiesDataHttpJSON;
import ma.ensao.swingmap.client.data.http.object.WorldCitiesDataHttpObject;
import ma.ensao.swingmap.client.data.http.xml.WorldCitiesDataHttpXML;
import ma.ensao.swingmap.shared.data.WorldCitiesData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.ezware.dialog.task.TaskDialogs;

public class WorldCitiesDataHttp extends SwingMapHttp implements WorldCitiesData {
	
//	private WorldCitiesDataAPI worldCitiesDataAPI = new WorldCitiesDataHttpXML();
//	private WorldCitiesDataAPI worldCitiesDataAPI = new WorldCitiesDataHttpJSON();
	private WorldCitiesDataAPI worldCitiesDataAPI = new WorldCitiesDataHttpObject();

	@Override
	public List<String> getCountryList() {
		List<String> countryList = new ArrayList<String>();
        try {
    		String uri = String.format("http://%s:%d/worldcities?getcountry=", getHost(), getPort());
    		HttpPost post = new HttpPost(uri);
    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("getcountry", null));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = getClient().execute(post);
			HttpEntity entity = response.getEntity();
	        InputStream stream = entity.getContent();
	        
	        countryList = worldCitiesDataAPI.getCountryList(stream);
	        stream.close();
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return countryList;
	}

	@Override
	public List<String> getCityList(String country) {
		List<String> cityList = new ArrayList<String>();
        try {
    		String uri = String.format("http://%s:%d/worldcities?getcity=", getHost(), getPort());
    		HttpPost post = new HttpPost(uri);
    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("getcity", null));
            nameValuePairs.add(new BasicNameValuePair("country", country));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = getClient().execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
		        InputStream stream = entity.getContent();
		        
		        cityList = worldCitiesDataAPI.getCityList(stream);
		        stream.close();
			}
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return cityList;
	}

	@Override
	public double[] getPosition(String country, String city) {
		double[] position = null;
        try {
    		String uri = String.format("http://%s:%d/worldcities?getposition=", getHost(), getPort());
    		HttpPost post = new HttpPost(uri);
    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("getposition", null));
            nameValuePairs.add(new BasicNameValuePair("country", country));
            nameValuePairs.add(new BasicNameValuePair("city", city));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = getClient().execute(post);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
		        InputStream stream = entity.getContent();
		        
		        position = worldCitiesDataAPI.getPosition(stream);
		        stream.close();
			}
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return position;
	}

}
