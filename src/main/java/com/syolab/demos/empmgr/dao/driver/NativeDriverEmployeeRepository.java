package com.syolab.demos.empmgr.dao.driver;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class NativeDriverEmployeeRepository {

    private final MongoCollection<Document> empCollection;
    @Value("${spring.data.mongodb.host}")
    private String host = "localhost";

    @Value("${spring.data.mongodb.port}")
    private Integer port = 27017;

    @Autowired
    public NativeDriverEmployeeRepository() {
        final MongoClient empDatabaseClient = new MongoClient(host, port);
        empCollection = empDatabaseClient.getDatabase("employees").getCollection("employee");
    }

    public List<Document> findAll() {
        return empCollection.find().limit(10)
                .into(new ArrayList<Document>());
    }

    public List<Document> findAll(Pageable pageable) {
        return empCollection.find().skip(pageable.getPageSize()*(pageable.getPageNumber()-1)).limit(pageable.getPageSize())
                .into(new ArrayList<Document>());
    }
}
