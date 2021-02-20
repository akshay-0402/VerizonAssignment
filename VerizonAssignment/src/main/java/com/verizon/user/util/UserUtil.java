package com.verizon.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verizon.user.entity.User;

public class UserUtil {
	
	private static ObjectMapper objectMapper;
	
	
	public static ObjectMapper getObjectMapper() {
		if (null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}
	
	public static String convertToJson(User user) {
		String jsonString = null;
		try {
			jsonString = getObjectMapper().writeValueAsString(user);
		} catch (JsonProcessingException jpe) {
			System.out.println("Exception while converting object to json");
			jpe.printStackTrace();
		}
		return jsonString;
	}
	
	public static <T> T convertJsonString(String json, Class<T> t) {
		T clazz = null;
		try {
			clazz = getObjectMapper().readValue(json, t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return clazz;
	}

}
