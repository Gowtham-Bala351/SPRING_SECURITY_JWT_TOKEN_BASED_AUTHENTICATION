package com.saveJsonServlet.saveJsonServlet.Bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SchoolData{
//school_data
	private String schoolName;
	
	@Id
	private int Schoolid;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getSchoolid() {
		return Schoolid;
	}

	public void setSchoolid(int schoolid) {
		Schoolid = schoolid;
	}	
}
//data_values