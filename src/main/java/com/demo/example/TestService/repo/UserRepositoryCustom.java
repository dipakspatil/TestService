package com.demo.example.TestService.repo;

import java.util.List;
import java.util.Set;

import com.demo.example.TestService.dto.UserResource;

public interface UserRepositoryCustom {

	List<UserResource> findUserByCitys(Set<String> cities);
}
