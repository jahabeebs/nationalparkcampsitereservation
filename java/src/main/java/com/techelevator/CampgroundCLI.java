package com.techelevator;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
	private ReservationDAO reservationDAO;
	private CampgroundDAO campgroundDAO;
	private SiteOfCampDAO siteDAO;
	private ParkDAO parkDAO;
	private long numChoice;
	private Campground campground;
	private PrintWriter output;
	private Scanner input;
	private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd/");

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();

	}

	public void run() {
		System.out.println("National Park Campsite Guide And Reservations");
		String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
		if (choice.equals(MAIN_MENU_OPTIONS_LIST)) {
			parks();

		} else if (choice.equals(MAIN_MENU_EXIT))

		{
			System.exit(0);
		}

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

		ArrayList<String> parkArrayList = new ArrayList<>();
		for (Park park : parkDAO.getAllParks()) {

			parkArrayList.add(park.getName());
		}

		String selectedPark = (String) menu.getChoiceFromOptions(parkArrayList.toArray());
		int parkId = parkDAO.getParkId(selectedPark);
		List<List<String>> rows = new ArrayList<>();
		List<String> headers = Arrays.asList("Campground      ", "Open Month  ", "Close Month  ", "Fee  ");
		rows.add(headers);

		for (Campground camp : campgroundDAO.getCampgroundByParkId(parkId)) {

			String campsite = camp.getName();
			double openDate = camp.getOpenFromMM();
			double closeDate = camp.getOpenToMM();
			String closeMonth = getMonth(closeDate);
			String openMonth = getMonth(openDate);
			BigDecimal fee2 = camp.getMoneyFee();
			String fee = fee2.toString();

			System.out.println("");

			rows.add(Arrays.asList(campsite, openMonth, closeMonth, "$" + fee));

		}
		System.out.println(formatAsTable(rows));

		List<Campground> campgrounds = campgroundDAO.getCampgroundByParkId(parkId);

		String choice = (String) menu.getChoiceFromOptions(RESERVATION_MENU_OPTIONS);
		if (choice.equals(RESERVATION_MENU_SEARCH_AVAILABLE)) {
			handleMakeReservation();
		} else if (choice.equals(RES_BACK))
			parks();
		{
			System.exit(0);
		}

		// String selectedCampGround = (String)
		// menu.getChoiceFromOptions(campgrounds.toArray());
		// System.out.println(selectedCampGround);

	}

	public static String formatAsTable(List<List<String>> rows) {
		int[] maxLengths = new int[rows.get(0).size()];
		for (List<String> row : rows) {
			for (int i = 0; i < row.size(); i++) {
				maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
			}
		}

		StringBuilder formatBuilder = new StringBuilder();
		for (int maxLength : maxLengths) {
			formatBuilder.append("%-").append(maxLength + 2).append("s");
		}
		String format = formatBuilder.toString();

		StringBuilder result = new StringBuilder();
		for (List<String> row : rows) {
			result.append(String.format(format, row.toArray(new String[0]))).append("\n");
		}
		return result.toString();
	}

	private LocalDate getArrivalDateUserInput() {
		LocalDate date = null;
		System.out.print("What is the arrival date? (YYYY/MM/DD/)");
		String userInput = input.nextLine();
		try {
			date = LocalDate.parse(userInput, dateTimeFormat);
		} catch (DateTimeParseException e) {
			output.println(userInput + " is not a valid option ***");
		}
		return date;
	}

	private LocalDate getDepartureDateUserInput() {
		LocalDate date = null;
		output.print("What is the departure date? (YYYY/MM/DD) ");
		output.flush();
		String userInput = input.nextLine();
		try {
			date = LocalDate.parse(userInput, dateTimeFormat);
		} catch (DateTimeParseException e) {
			output.println(userInput + " is not a valid option ***");
		}
		return date;
	}

	public String getMonth(double month) {
		return new DateFormatSymbols().getMonths()[(int) (month - 1)];
	}

	private void handleMakeReservation() {
		
		 Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		    System.out.println("What year will you be booking?");
		    try
		    {
		    String userYear = myObj.nextLine();
		    int userYearNum = Integer.parseInt(userYear);
		    int currentYear = Year.now().getValue();

		    if(userYearNum < currentYear ) {
		    	System.out.print("We hope to add space and timetravel in the future");
				parks();
		    }
		    else if (userYearNum > currentYear + 10 ) {
		    	System.out.print("We cannot make reservations for futher then 10 years... the park make not exist");
		    	parks();
		    }
		    else {
		    	System.out.print("great");
		    }
		    }
		    catch(NumberFormatException ex) {
		    	System.out.print("That is not a year");
		    }
			System.out.print("");
			System.out.print("");
		
		System.out.print("\nWhich site should be reserved (enter 0 to cancel)? >>> ");
		//getArrivalDateUserInput();
		
		System.out.print("What name should the reservation be made under? >>> ");
		//getDepartureDateUserInput();
		
	//	long customerReservationId = reservationDAO.makeReservation(numChoice, selectedCampGround, startDate, endDate);
		
	//	System.out.println("\nThe reservation has been made and the confirmation id is: " + customerReservationId + "\n\n");
		run();
	}
}
