package com.gpl3194.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {

	@Autowired
	private UserDaoService service;

	@Autowired
	private UserRepository userRepository;

	//GET /user
	//reterive all users resource
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){

		return userRepository.findAll();
	}

	//retriveUser(int id)
	@GetMapping("/jpa/users/{id}")
	public Optional<User> retrieveUser(@PathVariable int id) {

		Optional<User> user = userRepository.findById(id);

		if(!user.isPresent()) 
			throw new UserNotFoundException("id = " +id);


		//"all-users", SERVER_PATH + "/users"
		//retrieveAllUsers

		return user;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser=userRepository.save(user);
		//Created
		// /user/4

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {

		 userRepository.deleteById(id);


	}

}
