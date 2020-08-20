package com.techelevator;

import java.time.LocalDate;

public class Park {
	private long parkId;
	private String name;
	private String location;
	private LocalDate establishedDate;
	private int area; 
	private long annualVisitorCount;
	private String description;
	
	public long getParkId() {
		return parkId;
	}
	public void setParkId(long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablishedDate() {
		return establishedDate;
	}
	public void setEstablishedDate(LocalDate establishedDate) {
		this.establishedDate = establishedDate;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public long getAnnualVisitorCount() {
		return annualVisitorCount;
	}
	public void setAnnualVisitorCount(long annualVisitorCount) {
		this.annualVisitorCount = annualVisitorCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
