package com.syolab.demos.empmgr.dao.spring;

import com.syolab.demos.empmgr.dao.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class EmployeeRepositoryImpl implements CustomizedEmployeeRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    @Cacheable("employees")
    public Page<Employee> findAll(Pageable pageable) {
        Query query = new Query();
        query.skip(pageable.getPageSize()*(pageable.getPageNumber()-1));
        query.limit(pageable.getPageSize());
        List<Employee> empList =  mongoTemplate.find(query,Employee.class);
        Page<Employee> result = new PageImpl<Employee>(empList,
                new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                        pageable.getSort()), empList.size());
        return result;
    }
    @Override
    public Iterable<Employee> findAll() {
        Query query = new Query();
        return mongoTemplate.find(query,Employee.class);
    }
}
