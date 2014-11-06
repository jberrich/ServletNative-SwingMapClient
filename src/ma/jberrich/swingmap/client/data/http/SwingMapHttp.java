package ma.jberrich.swingmap.client.data.http;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ezware.dialog.task.TaskDialogs;

public class SwingMapHttp {
	
	private static Properties conf = null;
	
	private static String host;
	private static int    port;
	
	static {
		init();
	}

	private static void init() {
		try {
			conf = new Properties();
			FileInputStream in = new FileInputStream("./conf/config.ini");
			conf.load(in);
			in.close();
			
			host = conf.getProperty("client.http.host");
			port = Integer.parseInt(conf.getProperty("client.http.port"));
		} catch (Exception e) {
			TaskDialogs.showException(e);
		}
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
	
	public HttpClient getClient() {
		HttpClient client = HttpClientBuilder.create().build();
		return client;
	}

}
