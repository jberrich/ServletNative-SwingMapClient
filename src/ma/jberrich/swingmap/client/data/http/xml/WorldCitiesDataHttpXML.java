package ma.jberrich.swingmap.client.data.http.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import ma.jberrich.swingmap.client.data.http.api.WorldCitiesDataAPI;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.ezware.dialog.task.TaskDialogs;

public class WorldCitiesDataHttpXML implements WorldCitiesDataAPI {

	@Override
	public List<String> getCountryList(InputStream stream) {
		List<String> countryList = new ArrayList<String>();
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream));
	        SAXBuilder builder = new SAXBuilder();           
			Document document = builder.build(reader);
	        XPathFactory xpath = XPathFactory.instance();
			XPathExpression<Attribute> expression = xpath.compile("//country/@name", Filters.attribute());
			List<Attribute> list = expression.evaluate(document);
			for(Attribute attribute : list) {
				countryList.add(attribute.getValue());
			}
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return countryList;
	}

	@Override
	public List<String> getCityList(InputStream stream) {
		List<String> cityList = new ArrayList<String>();
		try {
	        Reader reader = new BufferedReader(new InputStreamReader(stream));
	        SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(reader);
	        XPathFactory xpath = XPathFactory.instance();
			XPathExpression<Attribute> expression = xpath.compile("//city/@name", Filters.attribute());
			List<Attribute> list = expression.evaluate(document);
			for(Attribute attribute : list) {
				cityList.add(attribute.getValue());
			}
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return cityList;
	}

	@Override
	public double[] getPosition(InputStream stream) {
		double[] position = null;
		try {
	        Reader reader = new BufferedReader(new InputStreamReader(stream));
	        SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(reader);
	        XPathFactory xpath = XPathFactory.instance();
			XPathExpression<Attribute> latitudeEXP = xpath.compile("/city/position/@latitude", Filters.attribute());
			XPathExpression<Attribute> longitudeEXP = xpath.compile("/city/position/@longitude", Filters.attribute());
			Attribute latitude = latitudeEXP.evaluateFirst(document);
			Attribute longitude = longitudeEXP.evaluateFirst(document);
			position = new double[2];
			position[0] = Double.parseDouble(latitude.getValue());
			position[1] = Double.parseDouble(longitude.getValue());
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return position;
	}

}
