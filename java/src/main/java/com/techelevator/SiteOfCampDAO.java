package com.techelevator;

import java.time.LocalDate;

import java.util.List;


public interface SiteOfCampDAO {

	List<SiteOfCamp> getAvailableSitesByCampgroundId(int campgroundId, LocalDate fromDate, LocalDate toDate);
	List<SiteOfCamp> getSitesByCampgroundId(int campgroundId);

	}


