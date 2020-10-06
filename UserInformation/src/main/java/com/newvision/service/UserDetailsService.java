package com.newvision.service;

import java.util.List;
import java.util.Optional;

import com.newvision.entity.User;

public interface UserDetailsService {
	public Long saveUser(User user);

	public User updateUser(User user,Long id);

	public Long deleteUser(Long id);

	public Optional<User> getOneUser(Long id);

	public List<User> getAllUser();

	public boolean isPresent(Long id);
}