package ma.jberrich.swingmap.client.jdom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import ma.jberrich.swingmap.client.data.ViewData;

public class JDOMViewData extends ViewData {

	public void load() {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File("tmp/view.xml"));
			Element root = document.getRootElement();
			this.setCountryIndex(Integer.parseInt(root.getChild("country").getAttributeValue("index")));
			this.setCityIndex(Integer.parseInt(root.getChild("city").getAttributeValue("index")));
			this.setTimezoneText(root.getChildText("timezone"));
			this.setCenterLatitude(Double.parseDouble(root.getChild("map").getChild("center").getAttributeValue("latitude")));
			this.setCenterLongitude(Double.parseDouble(root.getChild("map").getChild("center").getAttributeValue("longitude")));
			this.setZoomValue(Integer.parseInt(root.getChild("map").getChild("zoom").getAttributeValue("value")));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

	public void create() {
		Element root = new Element("view");
		Document document = new Document(root);
		Element country = new Element("country");
		country.setAttribute("index", Integer.toString(this.getCountryIndex()));
		Element city = new Element("city");
		city.setAttribute("index", Integer.toString(this.getCityIndex()));
		Element timezone = new Element("timezone");
		timezone.setText(this.getTimezoneText());
		Element map = new Element("map");
		Element center = new Element("center");
		Attribute latitude = new Attribute("latitude", Double.toString(this.getCenterLatitude()));
		Attribute longitude = new Attribute("longitude", Double.toString(this.getCenterLongitude()));
		center.setAttributes(Arrays.asList(new Attribute[] {latitude, longitude}));
		Element zoom = new Element("zoom");
		zoom.setAttribute("value", Integer.toString(this.getZoomValue()));
		map.addContent(center);
		map.addContent(zoom);
		root.addContent(country);
		root.addContent(city);
		root.addContent(timezone);
		root.addContent(map);
		XMLOutputter dataFile = new XMLOutputter(Format.getPrettyFormat());
		try {
			dataFile.output(document, new FileOutputStream("tmp/view.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(new File("tmp/view.xml"));
			Element root = document.getRootElement();
			root.getChild("country").getAttribute("index").setValue(Integer.toString(this.getCountryIndex()));
			root.getChild("city").getAttribute("index").setValue(Integer.toString(this.getCityIndex()));
			root.getChild("timezone").setText(this.getTimezoneText());
			root.getChild("map").getChild("center").getAttribute("latitude").setValue(Double.toString(this.getCenterLatitude()));
			root.getChild("map").getChild("center").getAttribute("longitude").setValue(Double.toString(this.getCenterLongitude()));
			root.getChild("map").getChild("zoom").getAttribute("value").setValue(Integer.toString(this.getZoomValue()));
			XMLOutputter dataFile = new XMLOutputter(Format.getPrettyFormat());
			dataFile.output(document, new FileOutputStream("tmp/view.xml"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
	}

}
