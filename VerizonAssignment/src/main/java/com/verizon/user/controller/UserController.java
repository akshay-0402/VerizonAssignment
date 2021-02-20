package com.verizon.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.user.entity.User;
import com.verizon.user.entity.Users;
import com.verizon.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path = "/getUser/{userId}")
	public ResponseEntity<User> getUserDetails(@PathVariable("userId") String userId) {
		User user = userService.getUserDetails(userId);
		ResponseEntity<User> userEntity = null;
		if (null == user) { 
			userEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			userEntity = new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return userEntity;
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<Users> getAllUserDetails() {
		System.out.println("UserController.getAllUserDetails()");
		Users users = userService.getAllUsers();
		if (users.getUsers().isEmpty()) {
			return new ResponseEntity<Users>(users, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Users>(users, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(path = "/deleteUser/{userId}")
	public ResponseEntity<String> deleteUserDetails(@PathVariable("userId") String userId) {
		boolean isDeleted = userService.deleteUser(userId);
		ResponseEntity<String> result = null;
		if (isDeleted) {
			result = new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			result = new ResponseEntity<String>("failue",HttpStatus.BAD_REQUEST);
		}
		return result;
	}
	
	@PostMapping(path = "/saveUser")
	public ResponseEntity<User> saveUserDetails(@RequestBody User userDetails) {
		User user = userService.saveUserDetails(userDetails);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
