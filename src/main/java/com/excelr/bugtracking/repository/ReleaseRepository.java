package com.excelr.bugtracking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excelr.bugtracking.entity.Release;

public interface ReleaseRepository extends JpaRepository<Release, Integer> {
	
	@Query("Select r FROM Release r WHERE lower(r.name) = lower(:name)")
	Optional<Release> findByName(@Param("name") String name);

}
