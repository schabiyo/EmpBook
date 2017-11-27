package com.syolab.demos.empmgr.spring;

import com.syolab.demos.empmgr.dao.domain.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, String> {

    //List all employees by their status
    List<Employee> findByEmployeeStatus(@Param("status") EmployeeStatus employeeStatus);
    // Paginate over a full-text search result
    @Query("{$text: {$search: ?0}}")
    Page<Employee> findEverything(@Param("criteria") String criteria, Pageable pageable);

    Page<Employee> findByFirstNameOrLastName(String what, TextCriteria criteria, Pageable pageable);
}
