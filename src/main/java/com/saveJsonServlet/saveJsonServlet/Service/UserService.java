package com.saveJsonServlet.saveJsonServlet.Service;




import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore.Entry;
import java.time.LocalDateTime;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.text.log.SysoCounter;
import com.saveJsonServlet.saveJsonServlet.Bean.ClassesData;
import com.saveJsonServlet.saveJsonServlet.Bean.LogsEn;

import com.saveJsonServlet.saveJsonServlet.Bean.SchoolData;
import com.saveJsonServlet.saveJsonServlet.Bean.SessionId;
import com.saveJsonServlet.saveJsonServlet.Bean.Usersk;
import com.saveJsonServlet.saveJsonServlet.Repo.ClassIdRepo;
import com.saveJsonServlet.saveJsonServlet.Repo.LogsRepo;
import com.saveJsonServlet.saveJsonServlet.Repo.SchoolDataRepo;
import com.saveJsonServlet.saveJsonServlet.Repo.TransactionRepository;
import com.saveJsonServlet.saveJsonServlet.Repo.UserRepo;


import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private LogPathInfo logpathInfo;

    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private TransactionRepository tran;
    
    @Autowired
    private SchoolDataRepo sclre;
 
    @Autowired
    private ClassIdRepo clsRepo;
    
    @Autowired
    private LogsRepo logs;
    
    
    
   
    
    public String toGenerateTransactionId(String name) {
    	String prefix = "MOD";
    	int digits = 7;
    	String maxTranId = tran.findByMaxTransactionId();
    	int nextId = 1;
    	if (maxTranId != null) {
    		try {
    			int lastNumber = Integer.parseInt(maxTranId.substring(prefix.length()));
                nextId = lastNumber + 1;
            } catch (NumberFormatException e) {
                nextId = 1;
            }
        }
    	String sessionId = String.format("%s%0" + digits + "d", prefix, nextId);
    	SessionId trann = new SessionId();
    	trann.setTransactionID(sessionId);
    	trann.setActive(true);
    	trann.setCreatedAt(LocalDateTime.now());
    	trann.setUsername(name);
    	tran.save(trann);

        return sessionId;
    }
    
    
    


    
    
    
    

    public JsonObject verify(Usersk user,HttpServletRequest h) {
    	
    	
    	Usersk us = userRepo.findByUsername(user.getUsername());
    	String status = us.getStatus();
if(status.equals("N")) {
    	JsonObject jsonObject = new JsonObject();
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    if (authentication.isAuthenticated()) {
    	us.setCountOfLogin(0);
    	userRepo.save(us);
    	Logger logS = logpathInfo.getLogFile(user.getUsername());
	    logS.info(user.getUsername()+"Loggined");
	    logS.info(logpathInfo.userInfo(h));
	    
	   JsonObject insideJson = new JsonObject();
	   JsonObject js = new JsonObject();
	   JsonObject js1 = new JsonObject();
	   js1.addProperty("ERROR_CODE", "MOD-202");
	   js1.addProperty("ERROR_DESC", "login pass");
	   js1.addProperty("LANG", ",ENG");
	   insideJson.add("Data", js);
	   insideJson.add("ERROR_SCRN", js1);
	   insideJson.addProperty("Token", jwtService.generateToken(user.getUsername()));
	   insideJson.addProperty("TXN-ID",toGenerateTransactionId(user.getUsername()));
	   jsonObject.add("Response", insideJson);
    }
   
    return jsonObject;
}
else {
	JsonObject jsonObject = new JsonObject();
	   JsonObject insideJson = new JsonObject();
	   JsonObject js = new JsonObject();
	   JsonObject js1 = new JsonObject();
	   js1.addProperty("ERROR_CODE", "MOD-201");
	   js1.addProperty("ERROR_DESC", "User Locked");
	   js1.addProperty("LANG", ",ENG");
	   insideJson.add("Data", js);
	   insideJson.add("ERROR_SCRN", js1);
	   jsonObject.add("Response", insideJson);
	   return jsonObject;
 }
}
    
  public String toInsertData(JsonObject mainObj) {
	 if(mainObj != null) {
		 String respose = "";
		 for(Map.Entry<String, JsonElement> entries : mainObj.entrySet()) {
			 if(entries.getKey().equals("TXN-DATA")) {
				JsonObject txnObj =  (JsonObject) entries.getValue();
				for(Map.Entry<String, JsonElement> twoEntry : txnObj.entrySet()) {
					if(twoEntry.getKey().equals("single-entry")) {
						JsonObject entry = (JsonObject) twoEntry.getValue();
						SchoolData scl = new SchoolData();
						scl.setSchoolName(entry.get("SchoolName").getAsString());
						scl.setSchoolid(entry.get("Schoolid").getAsInt());
						sclre.save(scl);
						respose = "School Data Inserted";
					}
					else if(twoEntry.getKey().equals("multiple-entry")) {
						JsonElement entry = twoEntry.getValue();
						for(JsonElement ele : entry.getAsJsonArray()) {
							JsonObject objEntries = ele.getAsJsonObject();
							
							ClassesData ls = new ClassesData();
							ls.setClassid(objEntries.get("Classid").getAsString());
							ls.setClassName(objEntries.get("ClassName").getAsString());
							ls.setSchoolid(objEntries.get("Schoolid").getAsString());
							clsRepo.save(ls);
			                respose = "Classes Data Inserted";
						}
					}
					else {
						respose  = "No Entries";
					}
				}
			 }
		 }
		 return respose;
	 }
	 else {
		 return "Body is null";
	 }
  }
  
  public String toInsertLogs(JsonObject obj) {
	  String logsREspose="";
	  JsonObject objJ = obj.get("session").getAsJsonObject();
	  String sessionId = "";
	  String userId = "";
	  for(Map.Entry<String, JsonElement> sss : objJ.entrySet()) {
		  if(sss.getKey().equals("TXN-ID")) {
			  sessionId = sss.getValue().getAsString();
		  }
		  else if(sss.getKey().equals("USER-ID")) {
			  userId = sss.getValue().getAsString();
		  }
	  }
	  
	  
	  
	  LogsEn lgsE = new LogsEn();
	  lgsE.setTxn_id(sessionId);
	  lgsE.setRequest(obj.toString().getBytes(StandardCharsets.UTF_8));
	  lgsE.setResponse(toInsertData(obj));
	  lgsE.setId(userId);
	  logs.save(lgsE);
	  logsREspose = "Logs and Data Inserted";
	  
	
	  return logsREspose;
  }
    
    
}
