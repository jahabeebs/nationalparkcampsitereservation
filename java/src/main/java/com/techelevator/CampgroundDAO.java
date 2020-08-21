package com.techelevator;

import java.util.List;

public interface CampgroundDAO {

	public List<Campground> getCampgroundByParkId(long id);

	public List<Campground> getparkIdByParkname(String name);
	
	
}
