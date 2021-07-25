package com.demo.example.TestService.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.example.TestService.dto.SomeBean;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	@GetMapping("/filteredbean")
	public MappingJacksonValue getFilterBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");

		Set<String> allowFields = new HashSet<>(Arrays.asList("field1","field3"));
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(getPropertyFilter(allowFields));
		return mapping;
	}

	/**
	 * @param someBean
	 * @param allowFields 
	 * @return
	 */
	private FilterProvider getPropertyFilter(Set<String> allowFields) {
		SimpleBeanPropertyFilter filter = allowFields.isEmpty() ? SimpleBeanPropertyFilter.serializeAll() : SimpleBeanPropertyFilter.filterOutAllExcept(allowFields);
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		return filters;
	}
	
	@GetMapping("/filteredbean-list")
	public MappingJacksonValue getListFilterBean() {
		
		
		
		List<SomeBean> list = Arrays.asList(new SomeBean("value1","value2","value3"),new SomeBean("value31","value32","value33"));
		Set<String> allowFields = new HashSet<>();
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(getPropertyFilter(allowFields));
		
		return mapping;
	}
	

}
