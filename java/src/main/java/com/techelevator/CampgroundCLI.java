package com.techelevator;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
	private static final String MAIN_MENU_OPTIONS_SEARCH_FOR_RES = "Search for a Reservation";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTIONS_LIST,
			MAIN_MENU_OPTIONS_SEARCH_FOR_RES, MAIN_MENU_EXIT };
	private static final String RES_BACK = "Back";
	private static final String RESERVATION_MENU_SEARCH_AVAILABLE = "Search Available Reservation";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] { RESERVATION_MENU_SEARCH_AVAILABLE,
			RES_BACK };
	private static final String CAMP_MENU_BACK = "Back";
	private static final String CAMP_MENU_OPTION_PICK_SITE = "Continue to pick site";
	private static final String[] SITE_MENU_OPTIONS = new String[] { CAMP_MENU_OPTION_PICK_SITE, CAMP_MENU_BACK };
	private Menu menu;
	private List<SiteOfCamp> availableSites = null;
	private LocalDate arrival;
	private LocalDate departure;
	private ReservationDAO reservationDAO;
	private CampgroundDAO campgroundDAO;
	private SiteOfCampDAO siteDAO;
	private ParkDAO parkDAO;
	private Long campgroundLong = (long) 0;
	Scanner userInput = new Scanner(System.in);
	private int parkId = 0;
	String userName;
	private String selectedSite = "";

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

		}

		else if (choice.equals(MAIN_MENU_OPTIONS_SEARCH_FOR_RES))

		{
			retrieveReservation();
		}

		else if (choice.equals(MAIN_MENU_EXIT))

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
			for (String item : items) {
				System.out.println("" + item);
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
		parkId = parkDAO.getParkId(selectedPark);
		List<List<String>> rows = new ArrayList<>();
		List<String> headers = Arrays.asList("CampgroundId   ", "Campground      ", "Open Month  ", "Close Month  ",
				"Fee  ");
		rows.add(headers);
		for (Campground camp : campgroundDAO.getCampgroundByParkId(parkId)) {

			String CampgroundId = camp.getCampground_id().toString();
			String campsite = camp.getName();
			double openDate = camp.getOpenFromMM();
			double closeDate = camp.getOpenToMM();
			String closeMonth = getMonth(closeDate);
			String openMonth = getMonth(openDate);
			BigDecimal fee2 = camp.getMoneyFee();
			String fee = fee2.toString();

			System.out.println("");

			rows.add(Arrays.asList(CampgroundId, campsite, openMonth, closeMonth, "$" + fee));

		}
		System.out.println(formatAsTable(rows));
		String choice = (String) menu.getChoiceFromOptions(RESERVATION_MENU_OPTIONS);
		if (choice.equals(RESERVATION_MENU_SEARCH_AVAILABLE)) {
			handleMakeReservation(parkId);
		} else if (choice.equals(RES_BACK))
			parks();
		{
			System.exit(0);

		}

	}

	public static String formatAsTable(List<List<String>> rows) {
		int[] maxLengths = new int[rows.get(0).size()];
		for (List<String> row : rows) {
			for (int i = 0; i < row.size() - 1; i++) {
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

	public String getMonth(double month) {
		return new DateFormatSymbols().getMonths()[(int) (month - 1)];
	}

	private void handleMakeReservation(int parkId) {

		List<List<String>> rows = new ArrayList<>();
		List<String> headers = Arrays.asList("CampgroundId   ", "Campground      ", "Open Month  ", "Close Month  ",
				"Fee  ");
		rows.add(headers);

		for (Campground camp : campgroundDAO.getCampgroundByParkId(parkId)) {

			int optionNum = 0;
			String optionString = String.valueOf(optionNum);
			String CampgroundId = camp.getCampground_id().toString();
			String campsite = camp.getName();
			double openDate = camp.getOpenFromMM();
			double closeDate = camp.getOpenToMM();
			String closeMonth = getMonth(closeDate);
			String openMonth = getMonth(openDate);
			BigDecimal fee2 = camp.getMoneyFee();
			String fee = fee2.toString();

			System.out.println("");

			rows.add(Arrays.asList(CampgroundId, campsite, openMonth, closeMonth, "$" + fee, optionString));

		}
		System.out.println(formatAsTable(rows));
		System.out.print("Select A Campground >>>");
		String userCamp = userInput.nextLine();
		campgroundLong = Long.parseLong(userCamp);
		String arrivalDate = "";
		String departureDate = "";

		String choice = (String) menu.getChoiceFromOptions(SITE_MENU_OPTIONS);
		if (choice.equals(CAMP_MENU_OPTION_PICK_SITE)) {

			System.out.print("What is the arrival date? (YYYY/MM/DD)");

			arrivalDate = userInput.nextLine();
			arrival = wrongDateFormat(arrivalDate);

			System.out.print("What is the departure date? (YYYY/MM/DD)");

			departureDate = userInput.nextLine();
			departure = wrongDateFormat(departureDate);

			System.out.print("What is your name or id? ");
			userName = userInput.nextLine();

			availableSites();

		} else if (choice.equals(CAMP_MENU_BACK))
			parks();
		{
			System.exit(0);
		}

	}

	LocalDate wrongDateFormat(String dateEnteredByUser) {
		LocalDate resultDate = null;

		if (dateEnteredByUser.length() != 10) {
			System.out.println("Invalid date format, please try again.");
			handleMakeReservation(parkId);
		} else {
			String[] dateArray = dateEnteredByUser.split("/");

			for (String s : dateArray) {
				try {
					Integer.parseInt(s);
				} catch (NumberFormatException e) {
					System.out.println("Invalid date format, please try again.");
					handleMakeReservation(parkId);
				}
			}
			if (dateArray.length != 3) {
				System.out.println("Invalid date format, please try again.");
				handleMakeReservation(parkId);
			}
			int Year = Integer.parseInt(dateArray[0]);
			int Month = Integer.parseInt(dateArray[1]);
			int Day = Integer.parseInt(dateArray[2]);

			if (Month < 1 || Month > 12) {
				System.out.println("Invalid Month, please try again.");
				handleMakeReservation(parkId);
			}
			if (Day < 1 || Day > 31) {
				System.out.println("Invalid Day, please try again.");
				handleMakeReservation(parkId);
			}
			if (Day > 30 && (Month == 2 || Month == 4 || Month == 6 || Month == 9 || Month == 11)) {
				System.out.println("Invalid Day, please try again.");
				handleMakeReservation(parkId);
			} else if (Day > 28 && (Month == 2)) {
				System.out.println("We are closed on leap days");
				handleMakeReservation(parkId);
			}
			resultDate = LocalDate.of(Year, Month, Day);
		}
		return resultDate;
	}

	private void availableSites() {

		System.out.println("Results Matching Your Search Dates");

		availableSites = siteDAO.sitesByDate(arrival, departure, campgroundLong);
		System.out.printf("\n %-15s %-15s \t%-15s %-15s \t%-15s", "Site No.", "Max Occup.", "Accessible?", "RV Length",
				"Utility");
		String trueOrFalse = "";
		String trueOrFalse2 = "";
		for (SiteOfCamp sites : availableSites) {
			if (sites.isAccessible()) {
				trueOrFalse = "Yes";
			} else {
				trueOrFalse = "No";
			}
			if (sites.isHasUtilities()) {
				trueOrFalse2 = "Yes";
			} else {
				trueOrFalse2 = "No";
			}

			System.out.printf("\n %-15s %-15s \t%-15s %-15s \t%-15s", sites.getSiteId(), sites.getMaxOccupancy(),
					trueOrFalse, sites.getMaxRvLength(), trueOrFalse2);
		}
		System.out.println("");
		System.out.println("Pick a site?");
		selectedSite = userInput.nextLine();
		long value1 = Long.parseLong(selectedSite);
		boolean isFalse = false;

		for (SiteOfCamp sites : availableSites) {
			sites.getSiteId();
			if (sites.getSiteId().equals(Long.parseLong(selectedSite))) {
				isFalse = true;
				break;
			}

		}

		if (isFalse == true) {
			reservationDAO.makeReservation(value1, userName, arrival, departure);
			System.out.println("");

			System.out.flush();
			System.out.println(
					"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

			System.out.println("The reservation is under: " + userName);
			System.out.println("Thank You for Booking");
			System.out.println("\n\n\n");

			run();
		}
		if (availableSites.size() == 0) {
			System.out.println("There are no available sites for the specified date range.");
			System.out.println("Would you like to enter an alternate date range? (Y)/(N)");
			Scanner yesOrNo = new Scanner(System.in);
			String noOrYes = yesOrNo.nextLine();
			if (noOrYes.toUpperCase().equals("Y")) {
				handleMakeReservation(parkId);
			} else if (noOrYes.toUpperCase().equals("N")) {
				System.out.println("Returning to Park Menu");
				parks();
			} else {
				System.out.println("Invalid entry, please try again.\n");
				availableSites();
				yesOrNo.close();
			}
		}
		handleMakeReservation(parkId);
	}

	public void retrieveReservation() {

		System.out.println("What is your reservation id #?");
		System.out.println("");
		String userInput1 = userInput.nextLine();
		long numInput = 0;
		Reservation reservation = null;
		String fromDate1 = "";
		String toDate1 = "";
		LocalDate fromDate = null;
		LocalDate toDate = null;
		String resId = "";
		long resIdnum = 0;
		while (reservation == null) {
		try {
			numInput = Long.parseLong(userInput1);
			reservation = reservationDAO.getReservationbyID(numInput);
			fromDate = reservation.getFromDate();
			toDate = reservation.getToDate();
			resIdnum = reservation.getSiteId();
			resId = String.valueOf(resIdnum);
			fromDate1 = fromDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
			toDate1 = toDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
		} catch (NumberFormatException e) {
			System.out.println("Oops, we couldn't find this id. Please try again!");
			retrieveReservation();
		  }
		 catch (NullPointerException e) {
			System.out.println("Oops, we couldn't find this id. Please try again!");
			retrieveReservation();
		  }
		}
		System.out.println(reservation);
		System.out.println(reservation.getName());
		System.out.println("Park ID: " + resId);
		System.out.println("From:");
		System.out.println(fromDate1);
		System.out.println("To:");
		System.out.println(toDate1);
	}

}
