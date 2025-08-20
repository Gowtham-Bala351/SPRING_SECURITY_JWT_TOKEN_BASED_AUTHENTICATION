package com.saveJsonServlet.saveJsonServlet.Service;

import java.net.http.HttpRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saveJsonServlet.saveJsonServlet.Bean.Usersk;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogPathInfo {
	
	 @Autowired
	    private JWTService jwtService;
	
	public String userInfo(HttpServletRequest a) {
		String method = a.getMethod();
		String uri = a.getRequestURI();
		String queryPara = a.getQueryString();
		//String ip = a.getRemoteAddr();
		// IP : "+ip
		return "Request Method "+method +
				", URI(API Endpoint) : "+uri+
				", Query parameters: " + queryPara;
	}
    public Logger getLogFile(String username) {
    	
    	LoggerContext logCon = (LoggerContext) LoggerFactory.getILoggerFactory();
    	
    	Logger logUser = logCon.getLogger("USER_"+username);
    	logUser.setAdditive(false); 
    	
    	PatternLayoutEncoder encode = new PatternLayoutEncoder();
    	encode.setContext(logCon);
    	encode.setPattern("%d{yyyy-MM-dd HH:mm:ss} %-5level %logger{36} - %msg%n");
    	encode.start();
  
    	FileAppender fileAppender = new FileAppender();
    	fileAppender.setContext(logCon);
    	fileAppender.setName("USER_"+username);
    	fileAppender.setFile("logs/"+username+" .log");
    	fileAppender.setEncoder(encode);
    	fileAppender.start();
    	
    	logUser.addAppender(fileAppender);
    	
    	
		return logUser;
    }
	public String getUserNamediffEndPointAuth(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
    	String token="";
    	String username = "";
    	Logger logS  = null;
        if (header != null && header.startsWith("Bearer ")) {
        	token = header.substring(7);
        	 username =  jwtService.extractUserName(token);
        }
        return username;
	}
	

}
