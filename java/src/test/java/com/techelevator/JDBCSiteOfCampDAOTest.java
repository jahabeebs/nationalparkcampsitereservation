package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import JDBC.JDBCSiteOfCampDAO;

public class JDBCSiteOfCampDAOTest extends DAOIntegrationTest {

	@Test
	public void testGetAvailableSitesByCampgroundId() {
		SiteOfCampDAO site  = new JDBCSiteOfCampDAO(super.getDataSource());
		List<Campground> campsiteTestList = new ArrayList<Campground>();
		SiteOfCamp campSite = new SiteOfCamp();
		campSite.setSiteNumber(1);
		campSite.setCampGroundId(4);
		campSite.setMaxOccupancy(10);
		assertEquals(campsiteTestList.toString(), site.getAvailableSitesByCampgroundId(4, LocalDate.of(2020, 8, 01), LocalDate.of(2020,  8, 10)).toString());
	}
}
	
//	@Test
//	public void testGetSitesByCampgroundId() {
//	}
//
