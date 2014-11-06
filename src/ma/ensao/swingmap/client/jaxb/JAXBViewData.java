package ma.ensao.swingmap.client.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ma.ensao.swingmap.client.data.ViewData;
import ma.ensao.swingmap.client.jaxb.view.Center;
import ma.ensao.swingmap.client.jaxb.view.City;
import ma.ensao.swingmap.client.jaxb.view.Country;
import ma.ensao.swingmap.client.jaxb.view.Map;
import ma.ensao.swingmap.client.jaxb.view.ObjectFactory;
import ma.ensao.swingmap.client.jaxb.view.View;
import ma.ensao.swingmap.client.jaxb.view.Zoom;

public class JAXBViewData extends ViewData {

	public void load() {
		try {
			JAXBContext context = JAXBContext.newInstance(View.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			View view = (View) unmarshaller.unmarshal(new File("tmp/view.xml"));
			this.setCountryIndex(view.getCountry().getIndex());
			this.setCityIndex(view.getCity().getIndex());
			this.setTimezoneText(view.getTimezone());
			this.setCenterLatitude(view.getMap().getCenter().getLatitude());
			this.setCenterLongitude(view.getMap().getCenter().getLongitude());
			this.setZoomValue(view.getMap().getZoom().getValue());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void create() {
		try {
			ObjectFactory factory = new ObjectFactory();
			JAXBContext context = JAXBContext.newInstance(View.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			View view = factory.createView();
			Country country = factory.createCountry();
			country.setIndex(this.getCountryIndex());
			City city = factory.createCity();
			city.setIndex(this.getCityIndex());
			Map map = factory.createMap();
			Center center = factory.createCenter();
			center.setLatitude(this.getCenterLatitude());
			center.setLongitude(this.getCenterLongitude());
			Zoom zoom = factory.createZoom();
			zoom.setValue(this.getZoomValue());
			map.setCenter(center);
			map.setZoom(zoom);
			view.setCountry(country);
			view.setCity(city);
			view.setTimezone(this.getTimezoneText());
			view.setMap(map);
			marshaller.marshal(view, new File("tmp/view.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			JAXBContext context = JAXBContext.newInstance(View.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			View view = (View) unmarshaller.unmarshal(new File("tmp/view.xml"));
			view.getCountry().setIndex(this.getCountryIndex());
			view.getCity().setIndex(this.getCityIndex());
			view.setTimezone(this.getTimezoneText());
			view.getMap().getCenter().setLatitude(this.getCenterLatitude());
			view.getMap().getCenter().setLongitude(this.getCenterLongitude());
			view.getMap().getZoom().setValue(this.getZoomValue());
			marshaller.marshal(view, new File("tmp/view.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

}
