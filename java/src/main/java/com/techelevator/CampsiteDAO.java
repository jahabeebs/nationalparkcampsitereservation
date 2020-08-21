package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface CampSiteDAO {

	public List <CampSite> getAvailableSitesByCampId(int campgroundId, LocalDate fromDate, LocalDate toDate);

	}

