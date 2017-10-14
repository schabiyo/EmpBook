package com.syolab.demos.empmgr.dao.spring;

import com.syolab.demos.empmgr.dao.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedEmployeeRepository {
     Page<Employee> findAll(Pageable pageable);
     Iterable<Employee> findAll();
}
