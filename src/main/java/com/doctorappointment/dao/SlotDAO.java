package com.doctorappointment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.doctorappointment.beans.Booking;
import com.doctorappointment.beans.Slot;

@Repository
public class SlotDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	static final String FETCH_AVAILABLE_SLOTS = "select * from calendar where DoctorId=? and IsBooked=0 and Date between ? and ?";
	static final String INSERT_BOOKING_ENTRY = "insert into bookings (PatientId, CalenderId, DoctorId) values (?,?,?)";
	static final String FETCH_BOOKING_ENTRY = "select * from bookings where PatientId=? and CalenderId=? and DoctorId=?";
	static final String FETCH_BOOKING_ENTRY_FOR_DELETE = "select * from bookings where BookingId=?";
	static final String UPDATE_AVAILABLE_SLOTS = "update calendar set IsBooked=1 where CalenderId=?";
	static final String CANCEL_BOOKING = "delete from bookings where BookingId=?";
	static final String UPDATE_CANCELLED_SLOT = "update calendar set IsBooked=0 where CalenderId=?";
	
	
	public List<Slot> getAvailabelSlotForADoctor(String docID, Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DATE, 2);
		
		Date date2 = cal.getTime();
		
		System.out.println(FETCH_AVAILABLE_SLOTS);
		
		System.out.println("Date1:" +date.toString() + "---and---Date2:"+ date2);
		
		List<Slot> slots = jdbcTemplate.query(FETCH_AVAILABLE_SLOTS, new Object[]{docID,date,date2}, new SlotRowMapper());
		System.out.println(slots);
		
		return slots;
	}
	
	public String bookSlot(String patientId, String docId, String slotId) throws DuplicateKeyException, Exception{
		
		List<Booking> bookings = null;
		try{
		int row = jdbcTemplate.update(INSERT_BOOKING_ENTRY,new Object[]{patientId,slotId,docId});
		if(row > 0){
			bookings = jdbcTemplate.query(FETCH_BOOKING_ENTRY,new Object[]{patientId,slotId,docId}, new BookingRowMapper());
			jdbcTemplate.update(UPDATE_AVAILABLE_SLOTS, new Object[]{slotId});
		}
		}catch(DuplicateKeyException e){
			throw e;
		}
		catch(Exception e){
			throw e;
		}
		
		return bookings.get(0).getBookingId();
	}
	
	
	public boolean cancelBooking(String bookingId) throws Exception{
		List<Booking> bookings = null;
		try{
		bookings =jdbcTemplate.query(FETCH_BOOKING_ENTRY_FOR_DELETE,new Object[]{bookingId}, new BookingRowMapper());
		String slotId = bookings.get(0).getSlotId();
		int row = jdbcTemplate.update(CANCEL_BOOKING, new Object[]{bookingId});
		
		if(row > 0) row = jdbcTemplate.update(UPDATE_CANCELLED_SLOT, new Object[]{slotId});
		if(row > 0) return true;
		return false;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	class SlotRowMapper implements RowMapper<Slot>
	{
	    @Override
	    public Slot mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Slot slot = new Slot();
	    	slot.setDate(rs.getDate("date"));
	    	slot.setStartTime(rs.getString("startTime"));
	    	slot.setEndTime(rs.getString("endTime"));
	    	slot.setSlotID(rs.getString("CalenderId"));
	    	slot.setIsBooked(rs.getString("IsBooked"));
	        return slot;
	    }
	}
	
	class BookingRowMapper implements RowMapper<Booking>
	{
	    @Override
	    public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Booking booking = new Booking();
	    	booking.setBookingId(rs.getString("BookingId"));
	    	booking.setSlotId(rs.getString("CalenderId"));
	    	booking.setPatientId(rs.getString("PatientId"));
	    	booking.setDoctorId(rs.getString("DoctorId"));
	        return booking;
	    }
	}
	
	

}
