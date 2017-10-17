package com.syolab.demos.empmgr.dao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//
//
@Document
//@Entity("employee")
public class Employee extends BaseEntity {

    // @org.mongodb.morphia.annotations.Id
    // @Property("_id")
    @Id
    private ObjectId employeeId;
    private EmployeeStatus employeeStatus;
    @TextIndexed String firstName;
    private String middleName;
    @TextIndexed String lastName;
    private String jobTitle;
    private String email;
    private Date birthDate;
    private Decimal128 salary;
    private Address address;

    private @Version
    @JsonIgnore
    Long version;

    private Employee() {this.employeeStatus = EmployeeStatus.UNKNOWN;}


    public Employee(String firstName, String middleName, String lastName, String email, String jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.employeeStatus = EmployeeStatus.ACTIVE;
    }

    public Employee(String firstName, String middleName, String lastName, String email, String jobTitle, EmployeeStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.employeeStatus = status;
    }

    public Decimal128 getSalary() {
        return salary;
    }

    public void setSalary(Decimal128 salary) {
        this.salary = salary;
    }

    public ObjectId getEmployeeId() {
        return employeeId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonIgnore
    public double[] getLocation () { return this.address.getLocation(); }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeStatus=" + employeeStatus +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", salary=" + salary +
                ", address=" + address +
                ", version=" + version +
                '}';
    }
}