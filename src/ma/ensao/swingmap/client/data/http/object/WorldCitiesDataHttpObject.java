package ma.ensao.swingmap.client.data.http.object;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import ma.ensao.swingmap.client.data.http.api.WorldCitiesDataAPI;
import ma.ensao.swingmap.shared.data.bean.CityListBean;
import ma.ensao.swingmap.shared.data.bean.CityPositionBean;
import ma.ensao.swingmap.shared.data.bean.CountryListBean;

import com.ezware.dialog.task.TaskDialogs;

public class WorldCitiesDataHttpObject implements WorldCitiesDataAPI {

	@Override
	public List<String> getCountryList(InputStream stream) {
		List<String> countryList = new ArrayList<String>();
		try {
			ObjectInput in = new ObjectInputStream(new BufferedInputStream(stream));
			CountryListBean bean = (CountryListBean) in.readObject();
			countryList = bean.getData();
			in.close();
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return countryList;
	}

	@Override
	public List<String> getCityList(InputStream stream) {
		List<String> cityList = new ArrayList<String>();
		try {
			ObjectInput in = new ObjectInputStream(new BufferedInputStream(stream));
			CityListBean bean = (CityListBean) in.readObject();
			cityList = bean.getData();
			in.close();
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return cityList;
	}

	@Override
	public double[] getPosition(InputStream stream) {
		double[] position = null;
		try {
			ObjectInput in = new ObjectInputStream(new BufferedInputStream(stream));
			CityPositionBean bean = (CityPositionBean) in.readObject();
			position = new double[2];
			position[0] = bean.getLatitude();
			position[1] = bean.getLongitude();
			in.close();
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return position;
	}

}
