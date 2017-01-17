package com.boris.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boris.userservice.dao.UserServiceDao;
import com.boris.userservice.model.UserDetails;

@RestController
public class UserServiceController {
	
	@Autowired
	UserServiceDao userServiceDao;
	
	@PostMapping("/users")
	public void registerUser(@RequestBody UserDetails userDetails) {
		userServiceDao.save(userDetails);
	}
	
	@GetMapping("/users/{userEmail}")
	public UserDetails retrieveUserDetails(@PathVariable String userEmail){
		return userServiceDao.findOne(userEmail);
	}

}
