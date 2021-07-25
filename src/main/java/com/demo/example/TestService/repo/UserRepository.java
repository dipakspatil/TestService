package com.demo.example.TestService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.example.TestService.dto.UserResource;

@Repository
public interface UserRepository extends JpaRepository<UserResource, String>, UserRepositoryCustom {

//	@Query(value= "SELECT * FROM user WHERE city = :city", nativeQuery = true)
	@Query("SELECT u FROM UserResource u WHERE u.city=:city")
	List<UserResource> findUsersByCity(@Param("city") String city);
	
}
