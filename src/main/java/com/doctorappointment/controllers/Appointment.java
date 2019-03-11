package com.doctorappointment.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorappointment.beans.Doctor;
import com.doctorappointment.beans.Slot;
import com.doctorappointment.business.Communication;
import com.doctorappointment.dao.DoctorDAO;
import com.doctorappointment.dao.PatientDAO;
import com.doctorappointment.dao.SlotDAO;

@RestController
@RequestMapping("appointment")
@CrossOrigin(origins = "*")
public class Appointment {
	
	@Autowired
	private DoctorDAO doctorDAO;
	
	@Autowired
	private SlotDAO slotDAO;
	
	@Autowired
	private PatientDAO patientDAO;
	
	@Autowired
	private Communication comm;
	
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@PostMapping("searchDoctors")
	public List<Doctor> searchDoctors(@RequestBody Map<String,String> filters)
	{
		//Map<String,String> filter;
		System.out.println(filters);
		List<Doctor> doctors = doctorDAO.searchDoctors(filters);
		return doctors;
	}
	
	@PostMapping("getSlots")
	public List<Slot> getAvailableSlot(@RequestBody Map<String,String> body)
	{
		String docID = body.get("docID");
		Date date = null;
		try {
			date = sdf.parse(body.get("date"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(body);
		List<Slot> dateSlots = slotDAO.getAvailabelSlotForADoctor(docID,date);
		
		return dateSlots;
	}
	
	
	@PostMapping("bookSlot")
	public String bookSlot(@RequestBody Map<String, String> body){
		String bookingId = null;
		String patientId = patientDAO.insertPatient(body);
		try{
			bookingId = slotDAO.bookSlot(patientId, body.get("docId"), body.get("slotId"));
			String messageBody= "Dear User,\n\nYour Appointment has been successfully booked.\nFor reference your booking id is: "+bookingId
					+"\n\nThanks,\nOpen Source Team";
			String subject = "Appointment Successfully booked!!";
			String toMailAddress = body.get("email");
			comm.sendMail(toMailAddress, messageBody, subject);
		}catch(DuplicateKeyException e){
			e.printStackTrace();
			bookingId = "ExceptionDuplicateSlot";
		}catch(Exception e){
			e.printStackTrace();
			bookingId = "Exception";
		}
		
		return bookingId;
	}
	
	@DeleteMapping("cancelBooking")
	public boolean deleteBooking(@RequestParam String bookingId){
		boolean isSuccess= false;
		try{
		isSuccess = slotDAO.cancelBooking(bookingId);
/*		String messageBody= "Dear User,\nYour Appointment has been cancelled successfully.\nFor reference your booking id was:"+bookingId;;
		String subject = "Appointment Successfully cancelled!!";;
		String toMailAddress = "";
		comm.sendMail(toMailAddress, messageBody, subject);*/
		}catch(Exception e){
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	

}
