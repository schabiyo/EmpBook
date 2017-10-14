package com.syolab.demos.empmgr.dao.spring;


import com.syolab.demos.empmgr.dao.domain.Address;
import com.syolab.demos.empmgr.dao.domain.Employee;
import com.syolab.demos.empmgr.dao.domain.EmployeeStatus;
import com.syolab.demos.empmgr.dao.spring.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
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
        Employee emp1 = new Employee("Sani","Tobre", "Chabi Yo", "schabiyo@syolab.io", "Solutions Architect");
        Employee emp2 = new Employee("Jonathan", null, "Tellier", "jtellier@syolab.io","Developer");
        Employee emp3 = new Employee("Pierre", "Carl", "Belanger","pbelanger@syolab.io","Finance Manager");
        Employee emp4 = new Employee("Annette", "Claudine", "Gagnon-Letailleur","agagnon@syolab.io","Marketing Manager");
        Employee emp5 = new Employee("Serge", null, "Poueme","spoueme@syolab.io","Solutions Architect");
        Employee emp6 = new Employee("Martin", "Donald", "PetitCoeur","mpetitcoeur@syolab.io","Developer", EmployeeStatus.FIRED);
        Employee emp7 = new Employee("Sam", null, "Harley","sharley@syolab.io","Solutions Architect",EmployeeStatus.FIRED);
        Employee emp8 = new Employee("Abike", "Samia", "Yacoubou","ayacoubou@syolab.io","Developer");
        Employee emp9 = new Employee("Siaka", null, "Baro","sbaro@syolab.io","Software Engineer");
        Employee emp10 = new Employee("Thierry", "Caillou", "Dorion","tdorion@syolab.io","QA Engineer",EmployeeStatus.FIRED);
        Employee emp11 = new Employee("Eric", "Mathieu", "Legaillard","elegaillard@syolab.io","DevOps Engineer");
        Employee emp12 = new Employee("Judicael", "Gentil", "Zounmevo","jzounmevo@syolab.io","Solutions Architect");
        Employee emp13 = new Employee("Parfait", "Legrand", "Dessouassi","pdessouassi@syolab.io","CEO");
        Employee emp14 = new Employee("Liam", "Akanni", "Chabi Yo","lchabiyo@syolab.io","CTO");
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
