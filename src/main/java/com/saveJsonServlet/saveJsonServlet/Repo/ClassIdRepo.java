package com.saveJsonServlet.saveJsonServlet.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saveJsonServlet.saveJsonServlet.Bean.ClassesData;

public interface ClassIdRepo extends JpaRepository<ClassesData, String> {

}
