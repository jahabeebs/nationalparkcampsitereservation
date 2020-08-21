package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;


import JDBC.JDBCCampgroundDAO;

public class JDBCCampgroundDAOTest extends DAOIntegrationTest {

	@Test
	public void testGetCampgroundByParkId() {
		CampgroundDAO campgroundDAO = new JDBCCampgroundDAO(super.getDataSource());
		List<Campground> campgroundTestList = new ArrayList<Campground>();
		Campground campground1 = new Campground();
		campground1.setName("Blackwoods");
		Campground campground2 = new Campground();
		campground2.setName("Seawall");
		Campground campground3 = new Campground();
		campground3.setName("Schoodic Woods");
		campgroundTestList.add(campground1);
		campgroundTestList.add(campground2);
		campgroundTestList.add(campground3);
		assertEquals(campgroundTestList.toString(), campgroundDAO.getCampgroundByParkId(1).toString());;
	}
	
//	@Test
//	public void testGetParkIdByParkName() {
//		CampgroundDAO campgroundDAO = new JDBCCampgroundDAO(dataSource);
//		List<Campground> campgroundTestList = new ArrayList<Campground>();
//		Campground campground1 = new Campground();
//		campground1.setName("Blackwoods");
//		Campground campground2 = new Campground();
//		campground2.setName("Seawall");
//		Campground campground3 = new Campground();
//		campground3.setName("Schoodic Woods");
//		campgroundTestList.add(campground1);
//		campgroundTestList.add(campground2);
//		campgroundTestList.add(campground3);
//		assertEquals(campgroundTestList, campgroundDAO.getparkIdByParkname("Blackwoods"));;
//	}
}
