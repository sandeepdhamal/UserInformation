package com.newvision.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newvision.entity.User;
import com.newvision.service.UserDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Sandeep Dhamal
 *
 */
@RestController
@RequestMapping(value = "/rest/User")
@Api("This is User Details data distributed component")
public class UserDetailsController {

	@Autowired
	private UserDetailsService service;

	/**
	 * @author Sandeep Dhamal
	 * @param User
	 * @save user in database
	 */
	@PostMapping(value = "/save")
	@ApiOperation(value = "Add new user")
	public ResponseEntity<String> save(@RequestBody User user) {
		ResponseEntity<String> resp = null;
		try {
			boolean present = false;

			if (user.getUserId() != null) {
				present = service.isPresent(user.getUserId());
			} else {
				present = false;

			}
			if (!present) {
				Long id = service.saveUser(user);
				resp = new ResponseEntity<String>("User '" + id + "' created", HttpStatus.CREATED);
			} else {

				throw new Exception("Id already Present");
			}
		} catch (Exception e) {
			resp = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			// e.printStackTrace();
		}
		return resp;
	}

	/**
	 * @author Sandeep Dhamal
	 * @Get all user From database
	 */
	@GetMapping(value = "/all")
	@ApiOperation(value = "Get All user ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users are fetch successfully") })
	public ResponseEntity<?> getAll() {
		ResponseEntity<?> resp = null;
		List<User> list = service.getAllUser();
		if (list == null || list.isEmpty()) {
			String message = "No Data Found";
			resp = new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
		} else {
			resp = new ResponseEntity<List<User>>(list, HttpStatus.OK);
		}
		return resp;
	}

	/**
	 * @author Sandeep Dhamal
	 * @param id
	 * @return delete based on id , if exist
	 */
	@DeleteMapping(value = "/delete/{id}")
	@ApiOperation(value = "Delete User By Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Delete successfully") })
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		ResponseEntity<String> resp = null;
		// check for exist
		boolean present = service.isPresent(id);
		if (present) {
			// if exist
			service.deleteUser(id);
			resp = new ResponseEntity<String>("Deleted '" + id + "' successfully", HttpStatus.OK);
		} else { // not exist
			resp = new ResponseEntity<String>("'" + id + "' User Not Exist", HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	/**
	 * @author Sandeep Dhamal
	 * @param User
	 * @param id
	 * @Update User data in database based on given id if user exist
	 * 
	 */
	@PutMapping(value = "/update/{id}")
	@ApiOperation(value = "Update Existing User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User Update successfully") })
	public ResponseEntity<String> update(@RequestBody User User, @PathVariable Long id) {
		ResponseEntity<String> resp = null;
		// check for id exist
		boolean present = service.isPresent(id);
		if (present) { // if exist
			service.updateUser(User, id);
			resp = new ResponseEntity<String>("Updated Successfully", HttpStatus.OK);
		} else {
			// not exist
			resp = new ResponseEntity<String>("Record '" + id + " ' not found", HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	/**
	 * @author Sandeep Dhamal
	 * @param id
	 * @return get Single User Records based on id
	 */
	@GetMapping(value = "/getOneUser/{id}")
	@ApiOperation(value = "Get Single User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Single User fetch successfully") })
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		ResponseEntity<?> resp = null;
		Optional<User> empData = service.getOneUser(id);
		if (!empData.isPresent()) {
			String message = "No Data Found";
			resp = new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
		} else {
			resp = new ResponseEntity<Optional<User>>(empData, HttpStatus.OK);
		}
		return resp;
	}
}
