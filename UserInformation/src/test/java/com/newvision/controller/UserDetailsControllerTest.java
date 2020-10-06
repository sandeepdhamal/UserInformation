package com.newvision.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newvision.entity.User;
import com.newvision.repository.UserRepository;

/**
 * @author Sandeep Dhamal
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest

public class UserDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void updateUserTest() throws Exception {
		final Long userId = 2L;
		final User user = new User();
		user.setUserId(userId);
		user.setFirstName("amit");
		user.setLastName("dhamal");
		Mockito.when(userRepository.existsById(userId)).thenReturn(true);
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		String json = mapper.writeValueAsString(user);
		mockMvc.perform(put("/rest/User/update/{id}", userId).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

	}

	@Test
	public void userNotFoundToUpdateServiceTest() throws Exception {
		final Long userId = 3L;
		final User user = new User();
		user.setUserId(userId);
		user.setFirstName("amit");
		user.setLastName("dhamal");
		Mockito.when(userRepository.existsById(userId)).thenReturn(false);
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
		String json = mapper.writeValueAsString(user);

		mockMvc.perform(put("/rest/User/update/{id}", userId).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());

	}

	@Test
	public void fetchAllUsersTest() throws Exception {
		User user;
		user = new User();
		user.setFirstName("sandeep");
		user.setMiddleaName("shivaji");
		user.setLastName("dhamal");

		Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
		this.mockMvc.perform(get("/rest/User/all").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void UsersNotFoundTest() throws Exception {

		Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList());
		this.mockMvc.perform(get("/rest/User/all").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void nullUsersTest() throws Exception {

		Mockito.when(userRepository.findAll()).thenReturn(null);
		this.mockMvc.perform(get("/rest/User/all").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void fetchOneUserByIdTest() throws Exception {
		final Long userId = 7L;
		final User user = new User();
		user.setFirstName("sandeep");
		user.setLastName("dhamal");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		this.mockMvc.perform(get("/rest/User/getOneUser/{id}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is(user.getFirstName())));

	}

	@Test
	public void fetchOneUserByIdNotFoundTest() throws Exception {
		final Long userId = 7L;
		final User user = new User();
		user.setFirstName("sandeep");
		user.setLastName("dhamal");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
		this.mockMvc.perform(get("/rest/User/getOneUser/{id}", userId)).andExpect(status().isNotFound());

	}

	@Test
	public void addUserTest() throws Exception {
		Long userId = 8L;
		User user = new User();
		user.setUserId(userId);
		user.setFirstName("sandeep");
		user.setLastName("dhamal");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
		this.mockMvc.perform(post("/rest/User/save").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isCreated());

	}

	@Test
	public void addUserForExceptionTest() throws Exception {
		Long userId = 8L;
		User user = new User();
		user.setUserId(8L);
		user.setFirstName("sandeep");
		user.setLastName("dhamal");
		Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(userId);
		Mockito.when(userRepository.existsById(userId)).thenReturn(true);
		this.mockMvc
				.perform(post("/rest/User/save").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isInternalServerError());

	}

	@Test
	public void addUserForNullExceptionTest() throws Exception {
		Long userId = 8L;
		User user = new User();
		user.setUserId(null);
		user.setFirstName("sandeep");
		user.setLastName("dhamal");
		Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(userId);
		Mockito.when(userRepository.existsById(userId)).thenReturn(true);
		this.mockMvc
				.perform(post("/rest/User/save").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isInternalServerError());

	}

	@Test
	public void deleteUserTest() throws Exception {
		final Long userId = 11L;
		Mockito.when(userRepository.existsById(userId)).thenReturn(true);
		MvcResult requestResult = this.mockMvc.perform(delete("/rest/User/delete/{id}", userId))
				.andExpect(status().isOk()).andReturn();

		String result = requestResult.getResponse().getContentAsString();
		System.out.println(result);
		assertEquals(result, "Deleted " + "'" + userId + "'" + " successfully");
	}

	@Test
	public void deleteUserNotFoundTest() throws Exception {
		final Long userId = 11L;
		Mockito.when(userRepository.existsById(userId)).thenReturn(false);
		MvcResult requestResult = this.mockMvc.perform(delete("/rest/User/delete/{id}", userId))
				.andExpect(status().isBadRequest()).andReturn();
		String result = requestResult.getResponse().getContentAsString();
		System.out.println(result);
		assertEquals(result, "'" + userId + "'" + " User Not Exist");
	}

	
}