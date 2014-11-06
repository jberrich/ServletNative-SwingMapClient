package ma.jberrich.swingmap.client.data.http.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import ma.jberrich.swingmap.client.data.http.api.TimezoneDataAPI;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.ezware.dialog.task.TaskDialogs;

public class TimezoneDataHttpXML implements TimezoneDataAPI {
	
	private String dateTimeFormat = "dd/MM/yyyy HH:mm:ss";

	@Override
	public Date getDateTimeZoneSystem(InputStream stream) {
		Date datetime = null;
		try {
	        Reader reader = new BufferedReader(new InputStreamReader(stream));
	        SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(reader);
	        XPathFactory xpath = XPathFactory.instance();
			XPathExpression<Attribute> expression = xpath.compile("/timezone/date/@time", Filters.attribute());
			Attribute attribute = expression.evaluateFirst(document);
			SimpleDateFormat formatDateTime = new SimpleDateFormat(dateTimeFormat);
			datetime = formatDateTime.parse(attribute.getValue());
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
		return datetime;
	}

}
