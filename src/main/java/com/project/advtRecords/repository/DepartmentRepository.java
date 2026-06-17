package com.project.advtRecords.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.advtRecords.entity.Department;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    List<Department> findByDepartmentName(String departmentName);

}