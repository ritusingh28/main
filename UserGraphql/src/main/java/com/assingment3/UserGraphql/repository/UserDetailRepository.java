package com.assingment3.UserGraphql.repository;

	import org.springframework.data.jpa.repository.JpaRepository;

import com.assingment3.UserGraphql.entity.UserDetail;

	
	public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
	    // You can add custom query methods if needed
	}


