package com.syolab.demos.empmgr.controller;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.syolab.demos.empmgr.dao.driver.NativeDriverEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private String mongoURIString = "mongodb://localhost";

    //@Autowired
   // public NativeDriverController() {

        //final MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURIString));
        //final MongoDatabase empDatabase = mongoClient.getDatabase("employees");

        //this.employeeRepository = new NativeDriverEmployeeRepository(empDatabase);
   // }

    @RequestMapping(path = "/employees")
    public ResponseEntity getEmployees() throws Exception {

        return Optional.ofNullable(employeeRepository.findAll())
                .map(a -> new ResponseEntity<List<Document>>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Accounts for user do not exist"));
    }

}
