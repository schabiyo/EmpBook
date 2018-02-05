package com.syolab.demos.empmgr.spring;


import com.syolab.demos.empmgr.dao.domain.Address;
import com.syolab.demos.empmgr.spring.Employee;
import com.syolab.demos.empmgr.dao.domain.EmployeeStatus;
import com.syolab.demos.empmgr.spring.EmployeeRepository;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
//@Profile("local")
public class DatabaseInitializer {

    private EmployeeRepository employeeRepository;

    @Autowired
    public DatabaseInitializer(EmployeeRepository orderRepository) {
        this.employeeRepository = orderRepository;
    }

    public void populate() {
        // Clear existing data
        employeeRepository.deleteAll();
        // Create a new shipping address for the customer
        Address address1 = new Address("1600 Pennsylvania Ave NW", null,
                "DC", "Washington", "United States", 20500);
        Address address2 = new Address("875 Howard St", null,
                "CA", "San Francisco", "United States", 94103);

        // Create a new employee
        Employee emp1 = new Employee("Sani","Tobre", "Chabi Yo", "sani.chabi-yo@mongodb.com", "Solutions Architect", new Double(51000), "@chabiyo", "+15142902389");
        Employee emp2 = new Employee("Jonathan", null, "Tellier", "jtellier@syolab.io","Developer",new Double(48000));
        Employee emp3 = new Employee("Pierre", "Carl", "Belanger","pbelanger@syolab.io","Finance Manager",new Double(120500));
        Employee emp4 = new Employee("Annette", "Claudine", "Gagnon-Letailleur","agagnon@syolab.io","Marketing Manager",new Double(110000));
        Employee emp5 = new Employee("Serge", null, "Poueme","spoueme@syolab.io","Solutions Architect",new Double(51000));
        Employee emp6 = new Employee("Martin", "Donald", "PetitCoeur","mpetitcoeur@syolab.io","Developer", new Double(48000),EmployeeStatus.FIRED);
        Employee emp7 = new Employee("Sam", null, "Harley","sharley@syolab.io","Solutions Architect",new Double(51000),EmployeeStatus.FIRED);
        Employee emp8 = new Employee("Abike", "Samia", "Yacoubou","ayacoubou@syolab.io","Developer",new Double(49000));
        Employee emp9 = new Employee("Siaka", null, "Baro","sbaro@syolab.io","Software Engineer",new Double(50000));
        Employee emp10 = new Employee("Thierry", "Caillou", "Dorion","tdorion@syolab.io","QA Engineer",new Double(35000),EmployeeStatus.FIRED);
        Employee emp11 = new Employee("Eric", "Mathieu", "Legaillard","elegaillard@syolab.io","DevOps Engineer",new Double(51000));
        Employee emp12 = new Employee("Judicael", "Gentil", "Zounmevo","jzounmevo@syolab.io","Solutions Architect",new Double(51000));
        Employee emp13 = new Employee("Parfait", "Legrand", "Dessouassi","pdessouassi@syolab.io","CEO",new Double(550000));
        Employee emp14 = new Employee("Liam", "Akanni", "Chabi Yo","lchabiyo@syolab.io","CTO",new Double(350000));
        emp1.setAddress(address1);
        emp2.setAddress(address2);
        emp3.setAddress(address1);
        emp4.setAddress(address2);
        emp5.setAddress(address1);
        emp6.setAddress(address2);
        emp7.setAddress(address1);
        emp8.setAddress(address2);
        emp9.setAddress(address1);

        emp11.setAddress(address2);
        emp12.setAddress(address1);
        emp13.setAddress(address2);
        emp14. setAddress(address1);

        emp1 = employeeRepository.save(emp1);
        emp2 = employeeRepository.save(emp2);
        emp3 = employeeRepository.save(emp3);
        emp4 = employeeRepository.save(emp4);
        emp5 = employeeRepository.save(emp5);
        emp6 = employeeRepository.save(emp6);
        emp7 = employeeRepository.save(emp7);
        emp8 = employeeRepository.save(emp8);
        emp9 = employeeRepository.save(emp9);
        emp10 = employeeRepository.save(emp10);
        emp11 = employeeRepository.save(emp11);
        emp12 = employeeRepository.save(emp12);
        emp13 = employeeRepository.save(emp13);
        emp14 = employeeRepository.save(emp14);



    }
}
