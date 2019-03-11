package com.doctorappointment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.doctorappointment.beans.Patient;
import com.doctorappointment.beans.Slot;

@Repository
public class PatientDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	final String UPDATE_PATIENT_DETAILS = "update patients set Firstname=?, LastName=?,CellPhone=?,Address=? where Email=?";
	final String INSERT_PATIENT_DETAILS = "insert into patients (Firstname,LastName,CellPhone,Address,Email) values (?,?,?,?,?)";
	final String FETCH_PATIENT_ID = "select PatientId from patients where Email=?";
	
	public String insertPatient(Map<String,String> body)
	{
		int row = 0;
		List<Patient> patients = null;
		row = jdbcTemplate.update(
				UPDATE_PATIENT_DETAILS, new Object[]{body.get("firstname"),body.get("lastName"),
						body.get("cellPhone"),body.get("address"),body.get("email")});
		System.out.println("row after update: "+row);
		if(row == 0)
			row = jdbcTemplate.update(INSERT_PATIENT_DETAILS, new Object[]{body.get("firstname"),body.get("lastName"),
					body.get("cellPhone"),body.get("address"),body.get("email")});
		System.out.println("row after update: "+row);
		if(row == 1)
			patients = jdbcTemplate.query(FETCH_PATIENT_ID, new Object[]{body.get("email")},new PatientRowMapper());
		
		return patients.get(0).getId();
	}
	
	
	class PatientRowMapper implements RowMapper<Patient>
	{
	    @Override
	    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Patient patient = new Patient();
	    	patient.setId(rs.getString("PatientId"));
	        return patient;
	    }
	}
	
	
	
	
	

}
