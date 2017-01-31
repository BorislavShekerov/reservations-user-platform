package com.boris.userservice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boris.userservice.dao.UserServiceDao;
import com.boris.userservice.model.UserDetails;
import com.boris.userservice.model.UserRole;

public class UserServiceControllerTest {
	
	@InjectMocks
	UserServiceController testObj;
	
	@Mock
	UserServiceDao userServiceDao;
	
	@Before
	public void setUp(){
		testObj = new UserServiceController();
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void registerUser(){
		UserDetails userDetails = new UserDetails("dummyUserEmail", "pass", Arrays.asList(UserRole.MEMBER));
		
		testObj.registerUser(userDetails);
		
		verify(userServiceDao).save(userDetails);
	}

	@Test
	public void retrieveUserDetails_userDetailsExist(){
		String dummyUserEmail = "dummyUserEmail";
		UserDetails userDetails = new UserDetails(dummyUserEmail, "pass", Arrays.asList(UserRole.MEMBER));
		
		when(userServiceDao.findOne(dummyUserEmail)).thenReturn(userDetails);
		
		UserDetails result = testObj.retrieveUserDetails(dummyUserEmail);
		
		verify(userServiceDao).findOne(dummyUserEmail);
		assertEquals("The user details mocked should be returned", userDetails, result);
	}

	@Test
	public void retrieveUserDetails_noSuchUserDetails(){
		String dummyUserEmail = "dummyUserEmail";
		when(userServiceDao.findOne(dummyUserEmail)).thenReturn(null);
		
		UserDetails result = testObj.retrieveUserDetails(dummyUserEmail);
		
		verify(userServiceDao).findOne(dummyUserEmail);
		assertEquals("The user details mocked should be returned", null, result);
	}
}
