package com.syolab.demos.empmgr.NativeDriver;

import com.syolab.demos.empmgr.morphia.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import java.util.List;
import org.bson.Document;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/driver")
public class NativeDriverController {

    @Autowired
    private NativeDriverEmployeeRepository employeeRepository;

    @RequestMapping(path = "/employees")
    public ResponseEntity getEmployees(Pageable pageable) throws Exception {

        return Optional.ofNullable(employeeRepository.findAll(pageable))
                .map(a -> new ResponseEntity<List<Document>>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Something bad happened with the Java Native Driver"));
    }


    @RequestMapping( method = RequestMethod.POST , path = "/employee", headers = "Accept=application/json")
    public ResponseEntity createEmployee(@RequestBody Document emp) throws Exception {
        System.out.println("emp:" + emp);
        employeeRepository.create(emp);
        return ResponseEntity.ok().build();
    }

}
