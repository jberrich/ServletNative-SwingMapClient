package ma.jberrich.swingmap.client.data.http.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import ma.jberrich.swingmap.client.data.http.api.TimezoneDataAPI;

import com.ezware.dialog.task.TaskDialogs;
import com.google.gson.stream.JsonReader;

public class TimezoneDataHttpJSON implements TimezoneDataAPI {
	
	private String dateTimeFormat = "dd/MM/yyyy HH:mm:ss";

	@Override
	public Date getDateTimeZoneSystem(InputStream stream) {
		Date datetime = null;
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(stream));
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if(name.equals("time")) {
					SimpleDateFormat formatDateTime = new SimpleDateFormat(dateTimeFormat);
					datetime = formatDateTime.parse(reader.nextString());			 
				} 
			}
			reader.endObject();
			reader.close();
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return datetime;
	}

}
