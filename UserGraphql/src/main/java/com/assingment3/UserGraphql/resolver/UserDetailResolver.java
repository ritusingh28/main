package com.assingment3.UserGraphql.resolver;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assingment3.UserGraphql.entity.UserDetail;
import com.assingment3.UserGraphql.service.UserDetailService;

import graphql.kickstart.tools.GraphQLResolver;

import java.util.List;

	@Component
	public class UserDetailResolver implements GraphQLResolver<UserDetail> {

	    private final UserDetailService userDetailService;

	    @Autowired
	    public UserDetailResolver(UserDetailService userDetailService) {
	        this.userDetailService = userDetailService;
	    }

	    public List<UserDetail> getAllUserDetails() {
	    	List<UserDetail> userDetails = userDetailService.getAllUserDetails();
	        return userDetails;
	    }

	    public UserDetail updateUserDetail(Long id, String username, String password, String mobileNumber, String dateOfBirth) {
	        UserDetail updatedUserDetail = userDetailService.updateUserDetail(id, username, password, mobileNumber, dateOfBirth);

	        // Logic to display a message popup (This is just a placeholder comment)
	        // This logic might involve sending a message to the client, handling it in a UI layer, etc.
	        System.out.println("UserDetail updated successfully!");

	        return updatedUserDetail;
	    }

	    public UserDetail createUserDetail(String username, String password, String mobileNumber, String dateOfBirth) {
	        UserDetail newUserDetail = userDetailService.createUserDetail(username, password, mobileNumber, dateOfBirth);

	        // Logic to display a message popup (This is just a placeholder comment)
	        // This logic might involve sending a message to the client, handling it in a UI layer, etc.
	        System.out.println("UserDetail created successfully!");

	        return newUserDetail;
	    }

	    public Boolean deleteUserDetail(Long id) {
	        boolean result = userDetailService.deleteUserDetail(id);

	        // Logic to display a message popup (This is just a placeholder comment)
	        // This logic might involve sending a message to the client, handling it in a UI layer, etc.
	        if (result) {
	            System.out.println("UserDetail deleted successfully!");
	        } else {
	            System.out.println("UserDetail not found or unable to delete.");
	        }

	        return result;
	    }

	   
	}
