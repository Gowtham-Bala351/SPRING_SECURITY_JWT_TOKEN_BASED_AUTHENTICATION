package com.saveJsonServlet.saveJsonServlet.Controller;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.saveJsonServlet.saveJsonServlet.Bean.Usersk;
import com.saveJsonServlet.saveJsonServlet.Repo.UserRepo;

import io.jsonwebtoken.security.Request;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AuthExceptionHandler {
	
	@Autowired
	UserRepo userrepo;
	

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public JsonObject handleAuthenticationException(AuthenticationException e,HttpServletRequest request) throws IOException {
    	JsonObject body = (JsonObject) request.getAttribute("parsedJson");
   	 Usersk user = togetuser(body);
   	 String username = user.getUsername();
   	Usersk userD =  userrepo.findByUsername(username);
   	int count = userD.getCountOfLogin();
   	if(count>=3) {
   		userD.setStatus("L");
   		userrepo.save(userD);
   	}
   	else {
   		userD.setCountOfLogin(count+1);
       	userrepo.save(userD);
   	}
   	

      JsonObject jsonObject = new JsonObject();
	   JsonObject insideJson = new JsonObject();
	   JsonObject js = new JsonObject();
	   JsonObject js1 = new JsonObject();
	   js1.addProperty("ERROR_CODE", "MOD-201");
	   js1.addProperty("ERROR_DESC", "login fail");
	   js1.addProperty("LANG", ",ENG");
	   insideJson.add("Data", js);
	   insideJson.add("ERROR_SCRN", js1);
	   jsonObject.add("Response", insideJson); 
	   return jsonObject;
    }
   
    public Usersk togetuser(JsonObject jsonEle) {
    	Usersk user = new Usersk();
    	JsonParser parser = new JsonParser();
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
		return user;
    }

}
