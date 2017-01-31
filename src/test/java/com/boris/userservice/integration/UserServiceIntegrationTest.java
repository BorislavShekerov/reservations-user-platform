package com.boris.userservice.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.boris.userservice.TestConfig;
import com.boris.userservice.dao.UserServiceDao;
import com.boris.userservice.model.UserDetails;
import com.boris.userservice.model.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class UserServiceIntegrationTest {
	
	private static final String DUMMY_EMAIL = "dummyEmail";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	UserServiceDao userServiceDao;
	
	UserDetails dummyUserDetails;
	@Before
	public void setup() {
		userServiceDao.deleteAll();
		dummyUserDetails = new UserDetails(DUMMY_EMAIL, "dummyPassword", Arrays.asList(UserRole.MEMBER));
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void registerUser() throws Exception{
		
		this.mockMvc
		.perform(post("/users/").contentType(MediaType.APPLICATION_JSON).content(asJsonString(dummyUserDetails))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isOk());
		
		
		UserDetails registeredUser = userServiceDao.findOne(DUMMY_EMAIL);
		
		assertNotNull("Null means registration did not work", registeredUser);
		assertEquals(registeredUser, dummyUserDetails);
	}

	@Test
	public void retrieveUserDetails_userFound() throws Exception{
		userServiceDao.save(dummyUserDetails);
		
		this.mockMvc.perform(get("/users/" + DUMMY_EMAIL).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.email").value(DUMMY_EMAIL));
	}

	@Test
	public void retrieveUserDetails_noUserForUsername() throws Exception{
		this.mockMvc.perform(get("/users/"+ DUMMY_EMAIL).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andDo(print()).andExpect(status().isOk()).andExpect(content().string(""));
	}
	
	private static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
