package com.saveJsonServlet.saveJsonServlet.Repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saveJsonServlet.saveJsonServlet.Bean.Usersk;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<Usersk, Integer> {

    Usersk findByUsername(String username);
  
}