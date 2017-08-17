package com.scottmarden.beltreviewer.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scottmarden.beltreviewer.models.Role;
import com.scottmarden.beltreviewer.models.User;
import com.scottmarden.beltreviewer.repositories.RoleRepository;
import com.scottmarden.beltreviewer.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
		
	public void saveWithUserRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepository.findByName("ROLE_USER"));
		userRepository.save(user);
	}
	
	public void saveWithAdminRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
		userRepository.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public List<Role> findAllRoles(){
		return roleRepository.findAll();
	}
	
	public List<Role> findRoleByName(String name) {
		return roleRepository.findByName(name);
	}
	
	public void destroyUser(Long id) {
		userRepository.delete(id);
	}
	
	public void createAdmin(Long id) {
		User user = userRepository.findOne(id);
		user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
		userRepository.save(user);
	}
	
	public List<String> stateList(){
		List<String> states = Arrays.asList("AL", "AK", "AR", "AZ", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NJ", "NH", "NM", "NV", "NY", "OH", "OK", "OR", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WV", "WI", "WY");
		return states;
	}
		
	
}
