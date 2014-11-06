package ma.jberrich.swingmap.client.data.http.api;

import java.io.InputStream;
import java.util.Date;

public interface TimezoneDataAPI {

	public Date getDateTimeZoneSystem(InputStream stream);

}
