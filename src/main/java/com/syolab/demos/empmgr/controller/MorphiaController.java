package com.syolab.demos.empmgr.controller;

import com.syolab.demos.empmgr.dao.domain.Employee;
import com.syolab.demos.empmgr.dao.morphia.MorphiaEmployeeRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/morphia")
public class MorphiaController {

    @Autowired
    private MorphiaEmployeeRepository employeeRepository;


    @RequestMapping(path = "/employees")
    public ResponseEntity getEmployees(Pageable pageable) throws Exception {
        return Optional.ofNullable(employeeRepository.findAll(pageable))
                .map(a -> new ResponseEntity<List<Employee>>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Accounts for user do not exist"));

    }
}
