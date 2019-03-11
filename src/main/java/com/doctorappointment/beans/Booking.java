package com.doctorappointment.beans;

public class Booking {
	
	String bookingId;
	String patientId;
	String doctorId;
	String slotId;
	
	
	
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}
	
	public String getSlotId() {
		return slotId;
	}


	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}


	
	

}
