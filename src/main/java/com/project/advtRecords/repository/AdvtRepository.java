package com.project.advtRecords.repository; // MUST match exactly!

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.advtRecords.entity.Advertisement;

@Repository
public interface AdvtRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a WHERE " +
           "(:advtNo IS NULL OR a.advtNo LIKE %:advtNo%) AND " +
           "(:advtName IS NULL OR a.advtName LIKE %:advtName%)")
    List<Advertisement> searchAdvertisements(
        @Param("advtNo") String advtNo, 
        @Param("advtName") String advtName
    );
}