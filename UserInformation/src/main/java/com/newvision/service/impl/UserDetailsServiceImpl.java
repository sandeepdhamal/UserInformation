package com.newvision.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newvision.entity.User;
import com.newvision.exception.UserNotFoundException;
import com.newvision.repository.UserRepository;
import com.newvision.service.UserDetailsService;

/**
 * @author Sandeep Dhamal
 *
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	/**
	 * @author SandeepDhamal
	 * @param user
	 * @return the newly created userId by request.
	 */

	@Override
	public Long saveUser(User user) {
		User userId = repo.save(user);
		return userId.getUserId();
	}

	/**
	 * @author SandeepDhamal
	 * @param user,id
	 * @ Update existing user data based on userId.
	 */
	@Override
	public User updateUser(User user, Long id) {
		User userData = null;
		try {
			Optional<User> updateUser = repo.findById(id);

			if (updateUser.isPresent()) {
				userData = updateUser.get();
				userData.setUserId(id);
				userData.setCreateDate(user.getCreateDate());
				userData.setModifiedDate(user.getModifiedDate());
				userData.setPassword(user.getPassword());
				userData.setReminderQueryQuestion(user.getReminderQueryQuestion());
				userData.setReminderQueryAnswer(user.getReminderQueryAnswer());
				userData.setEmailAddress(user.getEmailAddress());
				userData.setFirstName(user.getFirstName());
				userData.setMiddleaName(user.getMiddleaName());
				userData.setLastName(user.getLastName());
				userData.setJobTitle(user.getJobTitle());
				userData.setLoginDate(user.getLoginDate());
				userData.setAgreedToTermsOfUse(user.isAgreedToTermsOfUse());
				userData.setStatus(user.getStatus());
				userData.setEmailAddressVerified(user.isEmailAddressVerified());
				userData.setRoles(user.getRoles());
				repo.save(userData);

			} else {
				throw new UserNotFoundException(id);

			}
		} catch (Exception e) {
		}
		return userData;
	}

	/**
	 * @author SandeepDhamal
	 * @param id
	 * @deleted user by id.
	 */
	@Override
	public Long deleteUser(Long id) {
		repo.deleteById(id);
		return id;

	}

	/**
	 * @author SandeepDhamal
	 * @param id
	 * @ Return Single User Based on Id
	 */
	@Override
	public Optional<User> getOneUser(Long id) {

		return repo.findById(id);
	}

	/**
	 * @author SandeepDhamal
	 * @param null
	 * @ Return All User Data in List Type
	 */
	@Override
	public List<User> getAllUser() {
		List<User> list = repo.findAll();
		return list;
	}

	/**
	 * @author SandeepDhamal
	 * @param id
	 * @ Check User already present in db and return boolean value
	 */
	@Override
	public boolean isPresent(Long id) {
		return repo.existsById(id);
	}

}
