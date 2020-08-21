package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;


import com.techelevator.view.Menu;

import JDBC.JDBCCampgroundDAO;

import JDBC.JDBCParkDAO;
import JDBC.JDBCReservationDAO;
import JDBC.JDBCSiteOfCampDAO;

public class CampgroundCLI {
	
	private static final String MAIN_MENU_OPTIONS_LIST = "View Parks to find Campgrounds to make a Reservation";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] {MAIN_MENU_OPTIONS_LIST, MAIN_MENU_EXIT};
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
	private String selectedCampGround = "";
	private String selectedPark = "";
	private long selectedCampgroundId = 0;
	private long selectedSiteId = 0;
	private List<SiteOfCamp> availableSites = null;
	private LocalDate arrival;
	private LocalDate departure;
	private JDBCReservationDAO reservationDAO;
	private CampgroundDAO campgroundDAO;
	private SiteOfCampDAO siteDAO;
	private ParkDAO parkDAO;
	
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}
	

	public CampgroundCLI(DataSource dataSource) {
		this.menu = new Menu(System.in, System.out);
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		reservationDAO = new JDBCReservationDAO(dataSource);
		siteDAO = new JDBCSiteOfCampDAO(dataSource);
	}
	
	
	private void parks() {
		System.out.println("Select a Park");
		ArrayList <String> parkArrayList = new ArrayList<>();
		for (Park park : parkDAO.getAllParks()) {
			parkArrayList.add(park.getName());
		}
		selectedCampGround = (String)menu.getChoiceFromOptions(parkArrayList.toArray()); 

		campMenu();
	}
	
	private void campMenu() {
		String choice = (String)menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
		if(choice.equals("View Campgrounds")) {
			ArrayList <String> campgroundList = new ArrayList<>();
			for (Campground campground : campgroundDAO.getCampgroundByParkId(selectedCampgroundId)) {
				campgroundList.add(campground.getName());
				System.out.println(campgroundList);
			}
			String selectedSite = (String)menu.getChoiceFromOptions(campgroundList.toArray()); 
			System.out.println(campgroundList);
		}
		 else if (choice.equals(CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS)) {
			System.out.println("Search for Campground Reservation");
			reservations();
		} else if (choice.equals(CAMP_MENU_BACK)) {
			parks();
		}
	}

	
	
	private void reservations() {
		System.out.println("Select a Command");
		String choice = (String)menu.getChoiceFromOptions(RESERVATION_MENU_OPTIONS);
		if(choice.equals(RESERVATION_MENU_SEARCH_AVAILABLE)) {
			// 1) search available reservation
			//#1 Name openMonth closeMonth dailyFee

		} else if (choice.equals(RES_BACK)) {
			campMenu();
		}
	}

	public void run() {
	System.out.println("National Park Campsite Guide And Reservations");
		String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
		if(choice.equals(MAIN_MENU_OPTIONS_LIST)) {
			parks();
		} else if (choice.equals(MAIN_MENU_EXIT)) {
			System.exit(0);
		}
		
	}
	
	
	

}
