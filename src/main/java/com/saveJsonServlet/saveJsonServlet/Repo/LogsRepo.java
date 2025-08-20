package com.saveJsonServlet.saveJsonServlet.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saveJsonServlet.saveJsonServlet.Bean.LogsEn;


public interface LogsRepo extends JpaRepository<LogsEn, String> {

}
