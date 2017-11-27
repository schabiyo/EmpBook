package com.syolab.demos.empmgr.morphia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
                .orElseThrow(() -> new Exception("Something bad happened with Morphia"));
    }

    @RequestMapping( method = RequestMethod.POST , path = "/employee", headers = "Accept=application/json")
    public ResponseEntity createEmployee(@RequestBody Employee emp) throws Exception {
        System.out.println("emp:" + emp);
        employeeRepository.createEmployee(emp);
        return ResponseEntity.ok().build();

    }






}
