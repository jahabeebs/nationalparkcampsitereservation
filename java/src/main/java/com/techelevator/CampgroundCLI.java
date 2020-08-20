package com.techelevator;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.view.Menu;

public class CampgroundCLI {
	
	private static final String MAIN_MENU_OPTIONS_PARKS = "View Parks, Campgrounds, Make Reservation";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] {MAIN_MENU_OPTIONS_PARKS, MAIN_MENU_EXIT};
	private static final String PARK_MENU_DISPLAY_PARKS = "Select a Park";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_DISPLAY_PARKS };
	private static final String RES_BACK = "Back";
	private static final String RESERVATION_MENU_SEARCH_AVAILABLE = "Search Available Reservation";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] {RESERVATION_MENU_SEARCH_AVAILABLE,RES_BACK };
	private static final String CAMP_MENU_OPTION_ALL_CAMPGROUNDS = "View Campgrounds";
	private static final String CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS = "Search for Reservation";
	private static final String CAMP_MENU_BACK = "Back";
	private static final String[] CAMP_MENU_OPTIONS = new String[] {CAMP_MENU_OPTION_ALL_CAMPGROUNDS, CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS,CAMP_MENU_BACK};
	private Menu menu;
//	private Park selectedPark;
	private long selectedCampgroundId = 0;
	private long selectedSiteId = 0;
//	private List<Site> availableSites = null;
	private LocalDate arrival;
	private LocalDate departure;
//	private ReservationDAO reservationDAO;
//	private CampgroundDAO campgroundDAO;
//  private SiteDAO siteDAO;
//	private ParkDAO parkDAO
	
	public CampgroundCLI(Menu menu) {
		this.menu = menu;
	}
	

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}
	

	public CampgroundCLI(DataSource datasource) {
		// create your DAOs here
		//parkDAO = new JDBCParkDAO(dataSource);
	//	campgroundDAO = new JDBCCampgroundDAO(dataSource);
		//siteDAO = new JDBCSiteDAO(dataSource);
		//reservationDAO = new JDBCReservationDAO(dataSource);
		
	}

	public void run() {
	System.out.println("National Park Campsite Reservation");
		String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
		if(choice.equals(MAIN_MENU_OPTIONS_PARKS)) {
		//	Parks();
			System.out.println("menu works");
		} else if (choice.equals(MAIN_MENU_EXIT)) {
			System.exit(0);
		}
		
	}
	
	
	

}
