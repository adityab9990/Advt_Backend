package com.project.advtRecords.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.advtRecords.entity.Advertisement;

public interface AdvtRepository extends JpaRepository<Advertisement, Long> {

    // Swapped 'department' for 'advtName'
    @Query("SELECT a FROM Advertisement a WHERE " +
           "(:advtNo IS NULL OR a.advtNo LIKE CONCAT('%', :advtNo, '%')) AND " +
           "(:advtName IS NULL OR a.advtName LIKE CONCAT('%', :advtName, '%'))")
    List<Advertisement> searchAdvertisements(
        @Param("advtNo") String advtNo, 
        @Param("advtName") String advtName
    );

	List<Advertisement> findAll(Specification<Advertisement> specification);
}