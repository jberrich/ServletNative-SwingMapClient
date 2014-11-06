package ma.jberrich.swingmap.client;

import org.jdesktop.application.SingleFrameApplication;

public class SwingMapClient extends SingleFrameApplication {

	protected void startup() {
		this.show(new SwingMapClientView(this));
	}
	
	public static void main(String[] args) {
		launch(SwingMapClient.class, args);
	}

}
