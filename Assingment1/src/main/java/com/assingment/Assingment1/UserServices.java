package com.assingment.Assingment1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service

public class UserServices {

	private static final int PASSWORD_LENGTH = 8;

	@Autowired
	private UserRepo userRepository;

	// User Registration
	public User registerUser(User user) {
		// Generate and set password
		//user.setPassword(generatePassword());

		// Perform user registration
		return userRepository.save(user);
	}

	// User Update
	public User updateUser(User updatedUser) {
		// Check if the user exists
		User existingUser = userRepository.findById(updatedUser.getId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		// Update user properties
		existingUser.setUserName(updatedUser.getUserName());
		existingUser.setPassword(updatedUser.getPassword());

		// Save the updated user
		return userRepository.save(existingUser);
	}

	// User Deletion
	//public void deleteUser(Long userId) {
		// Check if the user exists
	//	userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		// Delete the user
		//userRepository.deleteById(userId);
	//}
	 public void deleteUser(Long userId) {
	        Optional<User> optionalUser = userRepository.findById(userId);
	        optionalUser.ifPresent(user -> {
	            user.setDeleted(true);
	            userRepository.save(user);
	        });
	}
	 public boolean isUserNameUnique(String userName) {
	        // Check both active and deleted users
	        return userRepository.countByUserNameAndDeleted(userName, false) == 0;
	 }

	// Password Generation
	private String generatePassword() {
		// Implement your strong password generation logic here
		// Example: Generate a random alphanumeric password of length PASSWORD_LENGTH
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder password = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			password.append(characters.charAt(random.nextInt(characters.length())));
		}

		return password.toString();
	}

	// Bulk User Registration with MultiThreading
	@Transactional
	public void bulkRegisterUsers(List<User> users) {
		// Implement multi-threaded bulk user registration logic here
		users.parallelStream().forEach(this::registerUser);
	}

	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	// Additional methods if needed
	
	// Retrieve user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // UserServices
    public List<User> getAllUsersWhereNotDeleted() {
        return userRepository.findAllByDeletedFalse();
    }
    public void bulkRegisterUsersFromCsv(InputStream inputStream) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            List<String[]> lines = reader.readAll();

            // Set the number of threads based on your preference
            int numThreads = 5;
            ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

            try {
                List<Runnable> tasks = createRegistrationTasks(lines);
                tasks.forEach(executorService::execute);
            } catch (Exception e) {
                // Handle exception
            } finally {
                executorService.shutdown();
            }
        }
    }

    private List<Runnable> createRegistrationTasks(List<String[]> lines) {
        // Create a Runnable for each user registration task
        return lines.stream()
                .map(line -> (Runnable) () -> {
                    // Process each line and register the user
                    String userName = line[0];
                    String password = line[1];
                    // ... Process other fields

                    User user = new User(userName, password);
                    registerUser(user);
                })
                .toList();
    }


}
