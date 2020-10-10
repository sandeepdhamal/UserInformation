package com.newvision.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.newvision.entity.User;
import com.newvision.repository.UserRepository;
import com.newvision.service.impl.UserDetailsServiceImpl;

/**
 * @author Sandeep Dhamal
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserDetailsServiceImplTest {

	@InjectMocks
	UserDetailsServiceImpl userDetailsService;

	@Mock
	UserRepository userRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @author Sandeep Dhamal
	 * @GetAllUser Data and test controller and service layer by moking userRepository
	 */
	@Test
	public void getAllUserTest() {
		User user;
		user = new User();
		user.setFirstName("sandeep");
		user.setMiddleaName("shivaji");
		user.setLastName("dhamal");
		Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
		List<User> empList = userDetailsService.getAllUser();
		assertEquals(1, empList.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void getOneUserTest() {
		final Long userId = 7L;
		User user;
		user = new User();
		user.setFirstName("sandeep");
		user.setMiddleaName("shivaji");
		user.setLastName("dhamal");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Optional<User> empList = userDetailsService.getOneUser(userId);
		assertEquals(true, empList.isPresent());
		assertEquals("sandeep", empList.get().getFirstName());
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	public void saveUserTest() {
		Long userId = 8L;
		User user;
		user = new User();
		user.setFirstName("sandeep");
		user.setMiddleaName("shivaji");
		user.setLastName("dhamal");

		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

		userDetailsService.saveUser(user);
		// assertEquals(userId, empId);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	public void deleteUserTest() {
		final Long userId = 7L;
		User user;
		user = new User();
		user.setUserId(userId);
		user.setFirstName("sandeep");
		user.setMiddleaName("shivaji");
		user.setLastName("dhamal");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Long id = userDetailsService.deleteUser(userId);
		assertEquals(userId, id);
		verify(userRepository, times(1)).deleteById(userId);
	}

	@Test
	public void isPresentTest() {
		final Long userId = 7L;

		Mockito.when(userRepository.existsById(userId)).thenReturn(true);
		Boolean isPresent = userDetailsService.isPresent(userId);
		assertEquals(true, isPresent);
		verify(userRepository, times(1)).existsById(userId);
	}

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
		User userData = userDetailsService.updateUser(user, userId);
		assertEquals("amit", userData.getFirstName());
		verify(userRepository, times(1)).save(user);

	}

	@Test
	public void updateUserNotFoundTest() throws Exception {
		final Long userId = 2L;
		final User user = new User();
		user.setUserId(userId);
		user.setFirstName("amit");
		user.setLastName("dhamal");
		Mockito.when(userRepository.existsById(userId)).thenReturn(false);
		User userData = userDetailsService.updateUser(user, userId);
		assertEquals(null, userData);
		verify(userRepository, times(0)).save(user);

	}

}
