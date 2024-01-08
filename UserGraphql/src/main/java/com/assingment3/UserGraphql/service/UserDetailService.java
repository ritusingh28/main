package com.assingment3.UserGraphql.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assingment3.UserGraphql.entity.UserDetail;
import com.assingment3.UserGraphql.repository.UserDetailRepository;


	@Service
	public class UserDetailService {
	
		 private final UserDetailRepository userDetailRepository;

		   @Autowired
		    public UserDetailService(UserDetailRepository userDetailRepository) {
		        this.userDetailRepository = userDetailRepository;
		    }

	    public UserDetail updateUserDetail(Long id, String username, String password, String mobileNumber, String dateOfBirth) {
	        Optional<UserDetail> optionalUserDetail = userDetailRepository.findById(id);
	        if (optionalUserDetail.isPresent()) {
	            UserDetail userDetail = optionalUserDetail.get();
	            userDetail.setUsername(username);
	            userDetail.setPassword(password);
	            userDetail.setMobileNumber(mobileNumber);
	            userDetail.setDateOfBirth(dateOfBirth);
	            return userDetailRepository.save(userDetail);
	        }
	        return null; // Handle error or throw an exception
	    }

	    public UserDetail createUserDetail(String username, String password, String mobileNumber, String dateOfBirth) {
	        UserDetail newUserDetail = new UserDetail();
	        newUserDetail.setUsername(username);
	        newUserDetail.setPassword(password);
	        newUserDetail.setMobileNumber(mobileNumber);
	        newUserDetail.setDateOfBirth(dateOfBirth);
	        return userDetailRepository.save(newUserDetail);
	    }

	    public boolean deleteUserDetail(Long id) {
	    	Optional<UserDetail> userDetailOptional = userDetailRepository.findById(id);

	        if (userDetailOptional.isPresent()) {
	            userDetailRepository.deleteById(id);
	            return true;
	        }

	        return false;  // UserDetail not found or unable to delete
	    }

	    public List<UserDetail> getAllUserDetails() {
	        return userDetailRepository.findAll();
	    }
	}

