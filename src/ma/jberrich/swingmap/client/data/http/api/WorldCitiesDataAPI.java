package ma.jberrich.swingmap.client.data.http.api;

import java.io.InputStream;
import java.util.List;

public interface WorldCitiesDataAPI {

	public List<String> getCountryList(InputStream stream);
	public List<String> getCityList(InputStream stream);
	public double[] getPosition(InputStream stream);

}
