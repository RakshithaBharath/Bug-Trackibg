package com.excelr.bugtracking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excelr.bugtracking.entity.Application;
import com.excelr.bugtracking.entity.Bug;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("SELECT a FROM Application a WHERE LOWER(a.name) = LOWER(:name)")
	Optional<Application> findByName(@Param("name") String name);

	@Query("SELECT a FROM Application a WHERE LOWER(a.owner) = LOWER(:owner)")
	Optional<Application> findByOwner(@Param("owner") String owner);

	@Query("SELECT a FROM Application a WHERE LOWER(a.description) = LOWER(:description)")
	Optional<Application> findBydescription(@Param("description") String description);


}
