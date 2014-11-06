package ma.jberrich.swingmap.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import ma.jberrich.swingmap.client.data.ViewData;
import ma.jberrich.swingmap.client.data.http.TimezoneDataHttp;
import ma.jberrich.swingmap.client.data.http.WorldCitiesDataHttp;
import ma.jberrich.swingmap.client.jaxb.JAXBViewData;
import ma.jberrich.swingmap.shared.data.TimezoneData;
import ma.jberrich.swingmap.shared.data.WorldCitiesData;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapKit.DefaultProviders;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.combobox.ListComboBoxModel;
import org.jdesktop.swingx.mapviewer.GeoPosition;

public class SwingMapClientView extends FrameView {

	private JPanel            mainPanel        = null;
	private JMenuBar          menuBar          = null;
	private JXMapKit          jxMapKit         = null;
	private JPanel            worldCitiesPanel = null;
	private JLabel            countryLabel     = null;
	private JComboBox<String> countryCBox      = null;
	private JLabel            cityLabel        = null;
	private JComboBox<String> cityCBox         = null;
	private JButton           timeButton       = null;
	private JPanel            timezonePanel    = null;            
	private JLabel            timezoneLabel    = null;

	private boolean started = false;
	
	private WorldCitiesData worldCitiesData = new WorldCitiesDataHttp();
	
	private TimezoneData timezoneData = new TimezoneDataHttp();

//	private ViewData viewData = new JDOMViewData();
	private ViewData viewData = new JAXBViewData();
	
	public SwingMapClientView(Application application) {
		super(application);
		initComponennts();
		initView();
	}
	
	private void initComponennts() {
		mainPanel        = new JPanel();
		jxMapKit         = new JXMapKit();
		worldCitiesPanel = new JPanel();
		timezonePanel    = new JPanel();
		
		mainPanel.setBackground(new Color(181, 208, 208));
		worldCitiesPanel.setOpaque(false);
		jxMapKit.setOpaque(false);
		timezonePanel.setBackground(new Color(181, 208, 208));
		
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu();
		JMenuItem saveMenuItem = new JMenuItem();
		JMenuItem exitMenuItem = new JMenuItem();	
		
		jxMapKit.setDataProviderCreditShown(true);
		jxMapKit.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));
		
		ActionMap actionMap = Application.getInstance(SwingMapClient.class).getContext().getActionMap(SwingMapClientView.class, this);
		
		ResourceMap resourceMap = Application.getInstance(SwingMapClient.class).getContext().getResourceMap(SwingMapClientView.class);
		fileMenu.setText(resourceMap.getString("fileMenu.text"));
		
		saveMenuItem.setAction(
				new AbstractAction() {
					
					public void actionPerformed(ActionEvent e) {
						viewData.setCountryIndex(countryCBox.getSelectedIndex());
						viewData.setCityIndex(cityCBox.getSelectedIndex());
						viewData.setTimezoneText(timezoneLabel.getText());
						viewData.setCenterLatitude(jxMapKit.getCenterPosition().getLatitude());
						viewData.setCenterLongitude(jxMapKit.getCenterPosition().getLongitude());
						viewData.setZoomValue(jxMapKit.getZoomSlider().getValue());
						viewData.save();
					}
					
				}
		);
		saveMenuItem.setName("saveMenuItem");
		saveMenuItem.setIcon(new ImageIcon(getClass().getResource("/ma/jberrich/swingmap/client/resources/icons/save.png")));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		saveMenuItem.setText(resourceMap.getString("saveMenuItem.text"));
		
		exitMenuItem.setAction(actionMap.get("quit"));
		exitMenuItem.setName("exitMenuItem");
		exitMenuItem.setIcon(new ImageIcon(getClass().getResource("/ma/jberrich/swingmap/client/resources/icons/exit.png")));
		
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
			
		countryLabel = new JLabel(resourceMap.getString("countryLabel.text"));
		countryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		countryCBox = new JComboBox<String>();
		AutoCompleteDecorator.decorate(countryCBox);
		countryCBox.setEditable(true);
		countryCBox.addActionListener(
				new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						updateCityList(countryCBox.getSelectedItem().toString());
					}
					
				}
		);
		
		cityCBox = new JComboBox<String>();
		AutoCompleteDecorator.decorate(cityCBox);
		cityCBox.setEditable(true);
		cityCBox.addActionListener(
				new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						double[] position = worldCitiesData.getPosition(countryCBox.getSelectedItem().toString(), cityCBox.getSelectedItem().toString());
						goTo(position[0], position[1]);
						timezoneLabel.setText("--/--/---- --:--:--");
					}
					
				}
		);
		
		cityLabel = new JLabel(resourceMap.getString("cityLabel.text"));
		cityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		timeButton = new JButton(new ImageIcon(getClass().getResource("/ma/jberrich/swingmap/client/resources/icons/time.png")));
//		timeButton.setFocusPainted(false);
//		timeButton.setRolloverEnabled(false);
		timeButton.setOpaque(false);
//		timeButton.setContentAreaFilled(false);
//		timeButton.setBorderPainted(false);
		timeButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		timeButton.addMouseListener(
				new MouseAdapter() {
				
					public void mouseEntered(MouseEvent e) {
						super.mouseEntered(e);
						timeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
					
					public void mouseExited(MouseEvent e) {
						super.mouseExited(e);
						timeButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
					
				}
		);
		timeButton.addActionListener(
				new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						double[] position = worldCitiesData.getPosition(countryCBox.getSelectedItem().toString(), cityCBox.getSelectedItem().toString());
						Date date = timezoneData.getDateTimeZoneSystem(position[0], position[1]);
						timezoneLabel.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
					}
					
				}
		);
		
		worldCitiesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.black, Color.black), 
																	resourceMap.getString("worldCitiesPanel.title"), 
																	TitledBorder.CENTER, 
																	TitledBorder.TOP,
																	worldCitiesPanel.getFont().deriveFont(Font.BOLD), 
																	Color.black));
		worldCitiesPanel.setLayout(new GridBagLayout());
		worldCitiesPanel.add(countryLabel,
	                         new GridBagConstraints(0, 0, 1, 1, 0, 0, 
	            		 			                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 
	            		                            new Insets(5, 5, 5, 5), 0, 0));
		worldCitiesPanel.add(countryCBox,
				             new GridBagConstraints(1, 0, 1, 1, 0, 0, 
				            		 			    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
				            		                new Insets(5, 0, 5, 5), 0, 0));
		worldCitiesPanel.add(cityLabel,
				             new GridBagConstraints(0, 1, 1, 1, 0, 0, 
				            		 			    GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 
				            		                new Insets(0, 5, 5, 5), 0, 0));
		worldCitiesPanel.add(cityCBox,
				             new GridBagConstraints(1, 1, 1, 1, 0, 0, 
				            		 			    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 
				            		                new Insets(0, 0, 5, 5), 0, 0));
		worldCitiesPanel.add(timeButton,
				             new GridBagConstraints(1, 2, 1, 1, 0, 0, 
				            		 			    GridBagConstraints.WEST, GridBagConstraints.NONE, 
				            		                new Insets(0, 0, 0, 5), 0, 0));
		
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(worldCitiesPanel, BorderLayout.NORTH);
		mainPanel.add(jxMapKit, BorderLayout.CENTER);
		
		timezonePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.black, Color.black), 
				                                                 resourceMap.getString("timezonePanel.title"), 
				                                                 TitledBorder.CENTER, 
				                                                 TitledBorder.TOP,
				                                                 timezonePanel.getFont().deriveFont(Font.BOLD), 
				                                                 Color.black));

		timezoneLabel = new JLabel();
		timezoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timezoneLabel.setFont(timezoneLabel.getFont().deriveFont(Font.BOLD, 14));
		timezoneLabel.setOpaque(false);
		
		timezonePanel.setLayout(new BorderLayout());		
		timezonePanel.add(timezoneLabel, BorderLayout.CENTER);
		getFrame().setIconImage(new ImageIcon(getClass().getResource("/ma/jberrich/swingmap/client/resources/icons/map.png")).getImage());
		setComponent(mainPanel);
		setMenuBar(menuBar);
		setStatusBar(timezonePanel);
	}
	
	private void initView() {
		openStreetMapView();
		File viewFile = new File("tmp/view.xml");
		List<String> countryList = worldCitiesData.getCountryList();
		ListComboBoxModel countryModel = new ListComboBoxModel(countryList);
		if(viewFile.exists()) {
			countryModel.setSelectedItem(countryList.get(viewData.getCountryIndex()));
		}else {
			countryModel.setSelectedItem("Maroc");
		}
		countryCBox.setModel(countryModel);
		List<String> cityList = worldCitiesData.getCityList(countryCBox.getSelectedItem().toString());
		ListComboBoxModel cityModel = new ListComboBoxModel(cityList);
		if(viewFile.exists()) {
			cityModel.setSelectedItem(cityList.get(viewData.getCityIndex()));
		}else {
			cityModel.setSelectedItem("Oujda");
		}
		cityCBox.setModel(cityModel);
		if(viewFile.exists()) {
			timezoneLabel.setText(viewData.getTimezoneText());
			jxMapKit.setCenterPosition(new GeoPosition(viewData.getCenterLatitude(), viewData.getCenterLongitude()));
			jxMapKit.getZoomSlider().setValue(viewData.getZoomValue());			
		}else {
			double[] position = worldCitiesData.getPosition("Maroc", "Oujda");
			goTo(position[0], position[1]);
			timezoneLabel.setText("--/--/---- --:--:--");			
		}
	}
	
	private void goTo(double latitude, double longitude) {
		jxMapKit.setAddressLocation(new GeoPosition(latitude, longitude));
	}
	
	private void updateCityList(final String country) {
		SwingUtilities.invokeLater(
				new Runnable() {
					
					@Override
					public void run() {
						if(started) {
							cityCBox.setModel(new ListComboBoxModel(worldCitiesData.getCityList(country)));
							getFrame().pack();							
						} else {
							started = true;
						}
					}
					
				}
		);
	}
	
	private void openStreetMapView() {
		jxMapKit.setDefaultProvider(DefaultProviders.OpenStreetMaps);
	}

}
