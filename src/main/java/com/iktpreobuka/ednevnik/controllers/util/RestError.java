package com.iktpreobuka.ednevnik.controllers.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.ednevnik.security.Views;

public class RestError {
	
	
   @JsonView(Views.Public.class) 
   private int code; 
   @JsonView(Views.Public.class)
   private String message; 
   
	public RestError(int code, String message) { 
		this.code = code; this.message = message; }  
	
	public int getCode() { 
		return code; }  
	public String getMessage() { 
		return message; } 
	
}
