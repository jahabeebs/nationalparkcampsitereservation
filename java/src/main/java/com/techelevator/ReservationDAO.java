package com.techelevator;

import java.time.LocalDate;

public interface ReservationDAO {
	
	public long makeReservation (long siteId, String name, LocalDate startDate, LocalDate endDate);
	
}
