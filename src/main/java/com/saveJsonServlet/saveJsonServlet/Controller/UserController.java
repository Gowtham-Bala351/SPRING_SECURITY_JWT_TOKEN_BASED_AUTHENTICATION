package com.saveJsonServlet.saveJsonServlet.Controller;

import java.net.http.HttpRequest;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoCounter;
import com.saveJsonServlet.saveJsonServlet.Bean.LogsEn;
import com.saveJsonServlet.saveJsonServlet.Bean.SessionId;
import com.saveJsonServlet.saveJsonServlet.Bean.Usersk;
import com.saveJsonServlet.saveJsonServlet.Repo.LogsRepo;
import com.saveJsonServlet.saveJsonServlet.Repo.TransactionRepository;
import com.saveJsonServlet.saveJsonServlet.Service.UserService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.pattern.parser.Parser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@RestController
public class UserController {
	@Autowired
	private UserService service;
	
	@Autowired
	private TransactionRepository repo;
	
	//private static final com.itextpdf.text.log.Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/login")
    public JsonObject login(@RequestBody String data,HttpServletRequest req) {
		Usersk user = new Usersk();
		JsonParser parser = new JsonParser();
		JsonObject jsonEle = (JsonObject) parser.parse(data);
		for(Entry<String, JsonElement> entries : jsonEle.entrySet()) {
			if(entries.getKey().equals("REQUEST")) {
				//System.out.println(entries.getKey());
				JsonObject json = (JsonObject) parser.parse(entries.getValue().toString());
			    for(Entry<String, JsonElement> entries2 : json.entrySet()) {
			       if(entries2.getKey().equals("TXT_DATA")){
			    	   JsonObject objj = (JsonObject) entries2.getValue();
			    	  // user.setUsername(objj.get("USER_ID").toString());
			    	   for(Entry<String, JsonElement> e : objj.entrySet()) {
			    		  JsonObject val = (JsonObject) e.getValue();
			    		  user.setUsername(val.get("USER_ID").getAsString());
			    		  user.setPassword(val.get("PASSWORD").getAsString()); 
			    	   }
			    	   }
			       }
			    }
			}
		boolean active = true;
		Boolean sessionStatus = repo.findByStatusUserid(user.getUsername(),active);
		System.out.println(sessionStatus);
		JsonObject obj;

		if (sessionStatus != null && sessionStatus) {
		    obj = togetSessionResponse();
		} else {
		    obj = service.verify(user, req);
		}

		return obj;
	}

	 public JsonObject togetSessionResponse() {
		JsonObject obj = new JsonObject();
		obj.addProperty("Status","User already login");
		return obj;
		
	}
	 
}
