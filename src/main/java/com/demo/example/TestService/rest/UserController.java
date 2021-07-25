package com.demo.example.TestService.rest;

import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.example.TestService.repo.PostRepository;
import com.demo.example.TestService.repo.UserRepository;
import com.demo.example.TestService.dto.HelloWorldBean;
import com.demo.example.TestService.dto.Post;
import com.demo.example.TestService.dto.UserResource;
import com.demo.example.TestService.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/user-serv")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping("/hello-world")
	public String getHelloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/hello-world-internationalized")
	public String getHelloWorldInternationalized(
//			@RequestHeader(name="Accept-Language",required=false) Locale locale
			) {
		return messageSource.getMessage("Good.morning.msg", null, "default Message", LocaleContextHolder.getLocale());
	}
	
	@GetMapping("/hello-bean")
	public HelloWorldBean getHelloWorldBean() {
		return new HelloWorldBean("hello-World-bean");
	}
	
	@GetMapping("/users")
	public List<UserResource> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(value= "/users", params = "city")
	public List<UserResource> getAllUsersWithCity(@RequestParam("city") String city) {
		System.out.println("Getting users with city : "+ city);
		return userRepository.findUsersByCity(city);
	}
	
	@GetMapping(value= "/users/cities")
	public List<UserResource> getAllUsersWithCities(@RequestBody List<String> cities) {
		System.out.println("Getting users with city : "+ cities);
		Set<String> citySet = new HashSet<>(cities);
		return userRepository.findUserByCitys(citySet);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> addUser(@Valid @RequestBody UserResource user) {
		user.setCreatedAt(new Date());
		UserResource savedUser = userRepository.save(user);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
//		return savedUser;
	}
	
	

	@GetMapping("/users/{id}")
	public EntityModel<UserResource> getUser(@PathVariable("id") String id) {
		System.out.println("retriving user with id :"+ id);
		 Optional<UserResource> optional = userRepository.findById(id);
		 if(!optional.isPresent())
			 throw new UserNotFoundException("id - " + id +" not found");
		 EntityModel<UserResource> model = EntityModel.of(optional.get());
		 WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(
				 								WebMvcLinkBuilder.methodOn(this.getClass())
				 								.getAllUsers());
		 model.add(linkToUsers.withRel("All-users"));
		return model; 
		 
//		 return new ResponseEntity<UserResource>(HttpStatus.NOT_FOUND);
				 
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> addUserPost(@PathVariable("id") String userid, @RequestBody Post post) {
	
		Optional<UserResource> user = userRepository.findById(userid);
		if(!user.isPresent()) {
			throw new UserNotFoundException("user not found with id "+userid);
		}
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> getUserPost(@PathVariable("id") String userid) {
	
		Optional<UserResource> user = userRepository.findById(userid);
		if(!user.isPresent()) {
			throw new UserNotFoundException("user not found with id "+userid);
		}
		UserResource usr = user.get();
		List<Post> posts = usr.getPosts();
		return posts;
	}
}
