package com.saveJsonServlet.saveJsonServlet.Bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClassesData {
	
	@Id
	private String Classid;
	private String ClassName;
	private String Schoolid;
	public String getClassid() {
		return Classid;
	}
	public void setClassid(String classid) {
		Classid = classid;
	}
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public String getSchoolid() {
		return Schoolid;
	}
	public void setSchoolid(String schoolid) {
		Schoolid = schoolid;
	}
	
	
//classes_data
	

}
