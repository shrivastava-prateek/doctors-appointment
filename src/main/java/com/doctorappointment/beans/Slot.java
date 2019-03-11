package com.doctorappointment.beans;

import java.util.Date;

public class Slot {
	
	private String startTime;
	private String endTime;
	private Date date;
	private String slotID;
	private String IsBooked;
	
	public Slot() {
		super();
	}

	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	public String getSlotID() {
		return slotID;
	}
	public void setSlotID(String slotID) {
		this.slotID = slotID;
	}
	
	
	
	public String getIsBooked() {
		return IsBooked;
	}
	public void setIsBooked(String isBooked) {
		IsBooked = isBooked;
	}

}
