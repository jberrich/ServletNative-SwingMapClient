package ma.jberrich.swingmap.client.data.http.object;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Date;

import ma.jberrich.swingmap.client.data.http.api.TimezoneDataAPI;
import ma.jberrich.swingmap.shared.data.bean.CityTimezoneBean;

import com.ezware.dialog.task.TaskDialogs;

public class TimezoneDataHttpObject implements TimezoneDataAPI {

	@Override
	public Date getDateTimeZoneSystem(InputStream stream) {
		Date datetime = null;
		try {
			ObjectInput in = new ObjectInputStream(new BufferedInputStream(stream));
			CityTimezoneBean bean = (CityTimezoneBean) in.readObject();
			datetime = bean.getDatetime();
			in.close();
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return datetime;
	}

}
