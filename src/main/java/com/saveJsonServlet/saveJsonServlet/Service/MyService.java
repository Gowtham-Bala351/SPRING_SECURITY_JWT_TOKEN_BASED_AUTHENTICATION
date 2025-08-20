package com.saveJsonServlet.saveJsonServlet.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class MyService {
	public JsonObject toJsonOBJ(JsonObject jsonDta) {
		JsonObject jsonConvert = null;
		for(Map.Entry<String, JsonElement> entriesFirst : jsonDta.entrySet()) {
			if(entriesFirst.getKey().equals("REQUEST")) {
			         jsonConvert = (JsonObject) entriesFirst.getValue();
			        
			}
		}
		return jsonConvert;	
	}
	public String togetFileName(JsonObject jsonDta) {
		String fileName = "";
		for(Entry<String, JsonElement> lastJson : toJsonOBJ(jsonDta).entrySet()) {
			if(lastJson.getKey().equals("FILE_NAME")) {
				fileName = lastJson.getValue().getAsString();
				}
			}
		return fileName;	
	} 
	public String togetAction(JsonObject jsonDta) {
		String action = "";
		for(Entry<String, JsonElement> lastJson : toJsonOBJ(jsonDta).entrySet()) {
			if(lastJson.getKey().equals("ACTION_RW")) {
				action = lastJson.getValue().getAsString();
				}
			}
		
		
		return action;	
	} 
	public String togetUserId(JsonObject jsonDta) {
		String user_id = "";
		for(Map.Entry<String, JsonElement> las : toJsonOBJ(jsonDta).entrySet()) {
			if(las.getKey().equals("BOT_USER_ID")) {
				user_id = las.getValue().getAsString();
			}
		}
		
		return user_id;	
	} 
	public String togetOperation(JsonObject jsonDta) {
		String operation = "";
		for(Map.Entry<String, JsonElement> las : toJsonOBJ(jsonDta).entrySet()) {
			if(las.getKey().equals("OPERATION")) {
				operation = las.getValue().getAsString();
			}
		}
		
		return operation;	
	}
	public String togetApplicationId(JsonObject jsonDta) {
		String operation = "";
		for(Map.Entry<String, JsonElement> las : toJsonOBJ(jsonDta).entrySet()) {
			if(las.getKey().equals("APP_ID")) {
				operation = las.getValue().getAsString();
			}
		}
		
		return operation;	
	} 
	
	
	public JsonObject toReadJson(String fileNameData,String scanApplicationScreenPath) throws IOException {  
		String fileName = scanApplicationScreenPath + "//" + fileNameData + ".json";
//		System.out.println(fileName);
//		FileReader fileReader = new FileReader(fileName);  
//		int i; 
//		String stringDta = "";// Using read method            
//		 while ((i = fileReader.read()) != -1) {                
//			  char a =  (char)i ;
//			  stringDta = stringDta+String.valueOf(a);
//		 }
//		 JsonParser parser = new JsonParser();
//		 JsonObject jsnObj = (JsonObject) parser.parse(stringDta);
		ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
       JsonNode arr =  objectMapper.readTree(file);
       String stringObj = arr.toString();
       JsonParser parser = new JsonParser();
       JsonObject jsnObj = (JsonObject) parser.parse(stringObj);
        return jsnObj; 
     }	
}
