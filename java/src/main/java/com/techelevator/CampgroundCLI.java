package com.techelevator;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTIONS_LIST, MAIN_MENU_EXIT };
	private static final String PARK_MENU_DISPLAY_PARKS = "Select a Park";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_DISPLAY_PARKS };
	private static final String RES_BACK = "Back";
	private static final String RESERVATION_MENU_SEARCH_AVAILABLE = "Search Available Reservation";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] { RESERVATION_MENU_SEARCH_AVAILABLE,
			RES_BACK };
	private static final String CAMP_MENU_OPTION_ALL_CAMPGROUNDS = "View Campgrounds";
	private static final String CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS = "Search for Reservation";
	private static final String CAMP_MENU_BACK = "Back";
	private static final String[] CAMP_MENU_OPTIONS = new String[] { CAMP_MENU_OPTION_ALL_CAMPGROUNDS,
			CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS, CAMP_MENU_BACK };
	private Menu menu;
	private String selectedCampGround = "";
	private Park selectedPark;

	private List<Campground> selectedCampgroundId;
	private long selectedSiteId = 0;
	private List<SiteOfCamp> availableSites = null;
	private LocalDate arrival;
	private LocalDate departure;
	private JDBCReservationDAO reservationDAO;
	private CampgroundDAO campgroundDAO;
	private SiteOfCampDAO siteDAO;
	private ParkDAO parkDAO;
	private long numChoice;

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

		System.out.println("");
		System.out.println("\nPark Information Screen");
		System.out.println("");
		System.out.println("Select a Park at Bottom");
		System.out.println("");

		Format formatter = new SimpleDateFormat("yyyy-MM-dd");

		for (Park park : parkDAO.getAllParks()) {

			System.out.println(park.getName() + " National Park");
			System.out.println("Established:  " + park.getEstablishedDate().toString());
			System.out.println(String.format("%-4s%8s", "Location:", park.getLocation()));
			System.out.println(String.format("%-4s%8d", "Area:", park.getArea()) + " sq km");
			long i = park.getAnnualVisitorCount();
			String s = String.valueOf(i);
			System.out.println("Annual Visitors:  " + s);
			String str = park.getDescription();
			String[] arrOfStr = str.split(".");
			String[] items = str.split("\\s*\\.\\s*");
			int indexItem = 1;
			for (String item : items) {
				System.out.println("" + item);
				indexItem++;
				for (String a : arrOfStr)
					System.out.println(a);
			}

			System.out.println("");
		}
		ArrayList<String> parkArrayList2 = new ArrayList<>();
		for (Park park : parkDAO.getAllParks()) {
			parkArrayList2.add(park.getDescription());
			parkArrayList2.add(park.getLocation());
			String s = String.valueOf(park.getAnnualVisitorCount());
			parkArrayList2.add(s);

		}

		ArrayList<String> parkArrayList = new ArrayList<>();
		for (Park park : parkDAO.getAllParks()) {

			parkArrayList.add(park.getName());
		}
		String selectedPark = (String) menu.getChoiceFromOptions(parkArrayList.toArray());
		int parkId = parkDAO.getParkId(selectedPark);
		List<Campground> campgrounds = campgroundDAO.getCampgroundByParkId(parkId);
		selectedCampGround = (String) menu.getChoiceFromOptions(campgrounds.toArray());

	}

	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month - 1];
	}

	private void campMenu() {
		String choice = (String) menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);

		System.out.println(selectedCampgroundId);
		System.out.println(CAMP_MENU_OPTION_ALL_CAMPGROUNDS);
		ArrayList<String> campgroundList = new ArrayList<>();
		System.out.println(selectedCampgroundId);
		selectedCampGround = (String) menu.getChoiceFromOptions(campgroundList.toArray());

		if (choice.equals(CAMP_MENU_OPTION_ALL_CAMPGROUNDS)) {

			String selectedSite = (String) menu.getChoiceFromOptions(campgroundList.toArray());
			System.out.println(campgroundList);
			System.out.println(campgroundList.toString());

		} else if (choice.equals(CAMP_MENU_SEARCH_AVAILABLE_RESERVATIONS)) {
			System.out.println("Search for Campground Reservation");
			reservations();
		} else if (choice.equals(CAMP_MENU_BACK)) {
			parks();
		}
	}

	private void reservations() {
		System.out.println("Select a Command");
		String choice = (String) menu.getChoiceFromOptions(RESERVATION_MENU_OPTIONS);
		if (choice.equals(RESERVATION_MENU_SEARCH_AVAILABLE)) {
		} else if (choice.equals(RES_BACK)) {
			campMenu();
		}
	}

	public void run() {
		System.out.println("National Park Campsite Guide And Reservations");
		String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
		if (choice.equals(MAIN_MENU_OPTIONS_LIST)) {
			parks();
		} else if (choice.equals(MAIN_MENU_EXIT)) {
			System.exit(0);
		}

	}

}
