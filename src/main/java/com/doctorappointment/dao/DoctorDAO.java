package com.doctorappointment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.doctorappointment.beans.Doctor;

@Repository
public class DoctorDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	final String FETCH_DOCTORS_SQL = "select * from doctor"; 
	
	
	public List<Doctor> searchDoctors(Map<String,String> filters)
	{
		List<String> whereClause = new ArrayList<String>();
		String finalWhereClause = "";
		String finalQuery = FETCH_DOCTORS_SQL;
		boolean whereFlag = false;
		
		if(filters.containsKey("docName") && filters.get("docName")!=null && filters.get("docName").trim()!="")
		{
			whereClause.add("lower(docName) like \"%"+filters.get("docName").toLowerCase()+"%\"");
			whereClause.add(" ");
			whereFlag = true;
		}
		if(filters.containsKey("city") && filters.get("city")!=null && filters.get("city").trim()!="")
		{	
			whereClause.add("lower(city) like \"%"+filters.get("city").toLowerCase()+"%\"");
			whereClause.add(" ");
			whereFlag = true;
		}
		if(filters.containsKey("address1")&& filters.get("address1")!=null && filters.get("address1").trim()!="")
		{
			whereClause.add("lower(address1) like \"%"+filters.get("address1").toLowerCase()+"%\"");
			whereClause.add(" ");
			whereFlag = true;
		}
		if(filters.containsKey("specialization")&& filters.get("specialization")!=null && filters.get("specialization").trim()!="")
		{
			whereClause.add("lower(specialization) like \"%"+filters.get("specialization").toLowerCase()+"%\"");
			whereClause.add(" ");
			whereFlag = true;
		}
		
		if(whereFlag){
			finalQuery+= " where ";
			for(int i=0;i<whereClause.size()-1;i++)
			{
				if(whereClause.get(i).equals(" "))
					finalWhereClause += " and ";
				else
					finalWhereClause += whereClause.get(i);
					
			}	
		}
		
		
		finalQuery += finalWhereClause;
		
		System.out.println("Final Query:----"+finalQuery);	
		
		List<Doctor> doctors = jdbcTemplate.query(finalQuery, new DoctorRowMapper());
		
		return doctors;
		
	}

	class DoctorRowMapper implements RowMapper<Doctor>
	{
	    @Override
	    public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Doctor doctor = new Doctor();
	    	doctor.setDocName(rs.getString("docName"));
	    	doctor.setCity(rs.getString("city"));
	    	doctor.setEmail(rs.getString("email"));
	    	doctor.setAboutDoctor(rs.getString("aboutDoctor"));
	    	doctor.setAddress1(rs.getString("address1"));
	    	doctor.setAddress2(rs.getString("address2"));
	    	doctor.setCellphone(rs.getString("cellphone"));
	    	doctor.setDegree(rs.getString("degree"));
	    	doctor.setFees(rs.getString("fees"));
	    	doctor.setSpecialization(rs.getString("specialization"));
	    	doctor.setState(rs.getString("state"));
	    	doctor.setZip(rs.getString("zip"));
	    	doctor.setDoctorID(rs.getString("Id"));
	        return doctor;
	    }
	}
}
