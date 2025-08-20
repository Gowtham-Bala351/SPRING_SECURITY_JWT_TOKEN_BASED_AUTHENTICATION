package com.saveJsonServlet.saveJsonServlet.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saveJsonServlet.saveJsonServlet.Bean.SessionId;


public interface TransactionRepository extends JpaRepository<SessionId, String>{
    
	@Query("SELECT MAX(t.transactionID) from SessionId t where t.transactionID like 'MOD%' ")
	String findByMaxTransactionId();
	

    @Query("SELECT s.active FROM SessionId s WHERE s.username = :name and s.active = :active")
    Boolean findByStatusUserid(@Param("name") String name,@Param("active") boolean active);

}
