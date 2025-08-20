package com.saveJsonServlet.saveJsonServlet.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saveJsonServlet.saveJsonServlet.Bean.SchoolData;

public interface SchoolDataRepo extends JpaRepository<SchoolData, Integer>{

}
