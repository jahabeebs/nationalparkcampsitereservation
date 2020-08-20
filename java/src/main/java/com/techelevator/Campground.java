package com.techelevator;

import java.math.BigDecimal;

public class Campground {
	private long campground_id;
	private String name;
	private double openFromMM;
	private double openToMM;
	private BigDecimal dailyFee;
	
	public Long getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(Long campground_id) {
		this.campground_id = campground_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getOpenFromMM() {
		return openFromMM;
	}
	public void setOpenFromMM(double openFromMM) {
		this.openFromMM = openFromMM;
	}
	public double getOpenToMM() {
		return openToMM;
	}
	public void setOpenToMM(double openToMM) {
		this.openToMM = openToMM;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
}
