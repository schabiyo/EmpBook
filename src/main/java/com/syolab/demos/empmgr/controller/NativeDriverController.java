package com.syolab.demos.empmgr.controller;

import com.syolab.demos.empmgr.dao.driver.NativeDriverEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import java.util.List;
import org.bson.Document;

@Controller
@RequestMapping("/api/driver")
public class NativeDriverController {

    @Autowired
    private NativeDriverEmployeeRepository employeeRepository;

    @RequestMapping(path = "/employees")
    public ResponseEntity getEmployees(Pageable pageable) throws Exception {

        return Optional.ofNullable(employeeRepository.findAll(pageable))
                .map(a -> new ResponseEntity<List<Document>>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Accounts for user do not exist"));
    }

}
