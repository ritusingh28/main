package com.assingment.Assingment1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    long countByUserNameAndDeleted(String userName, boolean deleted);
    List<User> findAllByDeletedFalse();

   // Optional<UserEntity> findByEmail(String email);

   // List<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);

    void deleteByUserName(String userName);

    

}     
