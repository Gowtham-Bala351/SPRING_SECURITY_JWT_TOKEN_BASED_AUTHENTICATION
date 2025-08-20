package com.saveJsonServlet.saveJsonServlet.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.saveJsonServlet.saveJsonServlet.Bean.UserPrincipal;
import com.saveJsonServlet.saveJsonServlet.Bean.Usersk;
import com.saveJsonServlet.saveJsonServlet.Repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usersk user = userRepo.findByUsername(username);
    if (user == null) {
        // System.out.println("User Not Found");
           throw new UsernameNotFoundException("user not found");
         }
        
        return new UserPrincipal(user);
    }
    
    
    
    
}