package ma.jberrich.swingmap.client.data.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ma.jberrich.swingmap.client.data.http.api.TimezoneDataAPI;
import ma.jberrich.swingmap.client.data.http.json.TimezoneDataHttpJSON;
import ma.jberrich.swingmap.client.data.http.object.TimezoneDataHttpObject;
import ma.jberrich.swingmap.client.data.http.xml.TimezoneDataHttpXML;
import ma.jberrich.swingmap.shared.data.TimezoneData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.ezware.dialog.task.TaskDialogs;

public class TimezoneDataHttp extends SwingMapHttp implements TimezoneData {

//	private TimezoneDataAPI timezoneDataAPI = new TimezoneDataHttpXML();
//	private TimezoneDataAPI timezoneDataAPI = new TimezoneDataHttpJSON();
	private TimezoneDataAPI timezoneDataAPI = new TimezoneDataHttpObject();

	@Override
	public Date getDateTimeZoneSystem(double latitude, double longitude) {
		Date datetime = null;
        try {
    		String uri = String.format("http://%s:%d/timezone", getHost(), getPort());
    		HttpPost post = new HttpPost(uri);
    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
            nameValuePairs.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = getClient().execute(post);
			HttpEntity entity = response.getEntity();
	        InputStream stream = entity.getContent();
	        
	        datetime = timezoneDataAPI.getDateTimeZoneSystem(stream);
	        stream.close();
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}	
		return datetime;
	}

	@Override
	public String getClientId() {
		return null;
	}

	@Override
	public void setClientId(String id) {		
	}

}
