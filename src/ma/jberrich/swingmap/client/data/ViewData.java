package ma.jberrich.swingmap.client.data;

import java.io.File;

public abstract class ViewData {

	private int    countryIndex    = 0;
	private int    cityIndex       = 0;
	private String timezoneText    = null;
	private double centerLatitude  = 0.;
	private double centerLongitude = 0.;
	private int    zoomValue       = 0;
	
	public ViewData() {
		if(new File("tmp/view.xml").exists()) {
			load();
		}
	}
	
	public abstract void load();
	
	public abstract void create();
	
	public abstract void update();
	
	public void save() {
		if(new File("tmp/view.xml").exists()) {
			update();
		}else {
			create();
		}
	}
	
	public int getCountryIndex() {
		return countryIndex;
	}
	
	public void setCountryIndex(int countryIndex) {
		this.countryIndex = countryIndex;
	}
	
	public int getCityIndex() {
		return cityIndex;
	}
	
	public void setCityIndex(int cityIndex) {
		this.cityIndex = cityIndex;
	}
	
	public String getTimezoneText() {
		return timezoneText;
	}
	
	public void setTimezoneText(String timezoneText) {
		this.timezoneText = timezoneText;
	}
	
	public double getCenterLatitude() {
		return centerLatitude;
	}
	
	public void setCenterLatitude(double centerLatitude) {
		this.centerLatitude = centerLatitude;
	}
	
	public double getCenterLongitude() {
		return centerLongitude;
	}
	
	public void setCenterLongitude(double centerLongitude) {
		this.centerLongitude = centerLongitude;
	}
	
	public int getZoomValue() {
		return zoomValue;
	}
	
	public void setZoomValue(int zoomValue) {
		this.zoomValue = zoomValue;
	}


}
