package com.demo.example.TestService.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.example.TestService.dto.Name;
import com.demo.example.TestService.dto.Person;
import com.demo.example.TestService.dto.PersonV2;

@RestController
public class VersioningController {

	// version with URI
	@GetMapping("v1/person")
	public Person getPersonV1() {
		return new Person("dipak patil");
	} 
	
	@GetMapping("v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2(new Name("dipak" , "patil"));
	} 
	
	//versioning with request param
	@GetMapping(value = "/person", params = "version=v1")
	public Person getPersonByParamV1(@RequestParam("version") String ver) {
		System.out.println("version " + ver);
		return new Person("dipak patil");
	} 
	
	@GetMapping(value = "/person", params = "version=v2")
	public PersonV2 getPersonByParamV2() {
		return new PersonV2(new Name("dipak" , "patil"));
	} 
	
	//versioning with header param
	@GetMapping(value = "/person", headers = "version=v1")
	public Person getPersonV1ByHeader(@RequestHeader("version") String ver) {
		System.out.println("version " + ver);
		return new Person("dipak patil");
	} 
		
	@GetMapping(value = "/person", headers = "version=v2")
	public PersonV2 getPersonV2ByHeader() {
		return new PersonV2(new Name("dipak" , "patil"));
	} 
	
	//Versioning with content negotiation / accept header param
	@GetMapping(value = "/person", produces = "application/v1+json")
	public Person getPersonV1ByProduce() {
		return new Person("dipak patil");
	} 
			
	@GetMapping(value = "/person", produces = "application/v2+json")
	public PersonV2 getPersonV2ByProduce() {
		return new PersonV2(new Name("dipak" , "patil"));
	} 
	
}
