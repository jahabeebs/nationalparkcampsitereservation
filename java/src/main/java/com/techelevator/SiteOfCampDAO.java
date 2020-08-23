package com.techelevator;

import java.time.LocalDate;

import java.util.List;

public interface SiteOfCampDAO {

	List<SiteOfCamp> sitesByDate(LocalDate arrival, LocalDate departure, Long id);

}
