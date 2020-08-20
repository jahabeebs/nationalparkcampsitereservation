package com.techelevator;

import java.math.BigDecimal;

public class Campground {
	private Long campground_id;
	private Long parkId;
	private String name;
	private double openFromMM;
	public Long getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(Long campground_id) {
		this.campground_id = campground_id;
	}
	public Long getParkId() {
		return parkId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
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
	public BigDecimal getMoneyFee() {
		return moneyFee;
	}
	public void setMoneyFee(BigDecimal moneyFee) {
		this.moneyFee = moneyFee;
	}
	private double openToMM;
	private BigDecimal moneyFee;
	
	@Override
	public String toString() {
		return name;
	}
}

