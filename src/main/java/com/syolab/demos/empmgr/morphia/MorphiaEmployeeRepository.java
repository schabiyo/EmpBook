package com.syolab.demos.empmgr.morphia;

import com.mongodb.MongoClient;
import org.mongodb.morphia.query.FindOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.mongodb.morphia.*;
import java.util.List;


@Service
public class MorphiaEmployeeRepository {

    //@Value("${spring.data.mongodb.host}")
    private String host = "localhost";

    //@Value("${spring.data.mongodb.port}")
    private Integer port = 27017;

    final Datastore datastore;

    @Autowired
    public MorphiaEmployeeRepository() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("com.syolab.demos.empmgr.dao.domain");
        final MongoClient empDatabaseClient = new MongoClient(host, port);
        datastore = morphia.createDatastore(empDatabaseClient, "employees");
        datastore.ensureIndexes();
    }

    public List<Employee> findAll(Pageable pageable) {
        List<Employee> emps = datastore.createQuery(Employee.class)
                .asList(new FindOptions().skip(pageable.getPageSize()*(pageable.getPageNumber()-1))
                    .limit(pageable.getPageSize()));
        return emps;
    }

    public void createEmployee(Employee  emp) {
        datastore.save(emp);
    }
}
