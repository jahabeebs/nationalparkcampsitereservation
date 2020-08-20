package com.techelevator;

import java.time.LocalDate;

public class Reservation {
	
	private long resId;
	private long siteId;
	private String name;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate makeDate;
	
	
	public long getResId() {
		return resId;
	}
	public void setResId(long resId) {
		this.resId = resId;
	}
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(LocalDate makeDate) {
		this.makeDate = makeDate;
	}

	public String toString() {
		return "Reservation id: " + resId;
	}
	

}
