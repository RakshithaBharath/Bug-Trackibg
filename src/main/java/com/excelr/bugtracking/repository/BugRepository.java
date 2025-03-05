package com.excelr.bugtracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excelr.bugtracking.entity.Bug;

public interface BugRepository extends JpaRepository<Bug, Integer> {

	@Query("Select b from Bug b where b.application.id = :id")
	List<Bug> getByApplicationId(@Param("id") int id);

	@Query("Select b from Bug b where b.release.id = :id")
	List<Bug> getByReleaseId(@Param("id") int releaseId);

	List<Bug> findByStatus(String status);

}
