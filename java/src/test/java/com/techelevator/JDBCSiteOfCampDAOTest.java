package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import JDBC.JDBCSiteOfCampDAO;

public class JDBCSiteOfCampDAOTest extends DAOIntegrationTest {

	@Test
	public void testSitesByDate() {
		SiteOfCampDAO site  = new JDBCSiteOfCampDAO(super.getDataSource());

		List<Campground> campsiteTestList = new ArrayList<Campground>();
		SiteOfCamp campSite = new SiteOfCamp();
		campSite.setSiteNumber(1);
		campSite.setCampGroundId(4);
		campSite.setMaxOccupancy(10);
		assertEquals(276, site.sitesByDate(LocalDate.of(2020, 8, 10), LocalDate.of(2020,  8, 11), (long)0).size());
		}	

	}

//List<Integer> campsiteTestList = new ArrayList<Integer>();
//SiteOfCamp campSite = new SiteOfCamp();
//campSite.setSiteNumber(1);
//campSite.setCampGroundId(4);
//campSite.setMaxOccupancy(10);
//campsiteTestList.add(campSite.getSiteNumber());
//campsiteTestList.add(campSite.getCampGroundId());
//campsiteTestList.add(campSite.getMaxOccupancy());
//List<SiteOfCamp> campsiteFunctionTestList = new ArrayList<SiteOfCamp>();