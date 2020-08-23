package com.techelevator;

public class SiteOfCamp {
	
	private String name;
	private long siteId;
	private int campGroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRvLength;
	private boolean hasUtilities;

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public int getCampGroundId() {
		return campGroundId;
	}

	public void setCampGroundId(int campGroundId) {
		this.campGroundId = campGroundId;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public int getMaxRvLength() {
		return maxRvLength;
	}

	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}

	public boolean isHasUtilities() {
		return hasUtilities;
	}

	public void setHasUtilities(boolean hasUtilities) {
		this.hasUtilities = hasUtilities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}





}
