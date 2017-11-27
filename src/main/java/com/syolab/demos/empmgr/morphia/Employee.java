package com.syolab.demos.empmgr.morphia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syolab.demos.empmgr.dao.domain.Address;
import com.syolab.demos.empmgr.dao.domain.BaseEntity;
import com.syolab.demos.empmgr.dao.domain.EmployeeStatus;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

//
//
@JsonSerialize
@Entity("employee")
public class Employee extends BaseEntity {

    @org.mongodb.morphia.annotations.Id
    @Property("_id")
    @Id
    private ObjectId employeeId;
    private EmployeeStatus employeeStatus;
    @TextIndexed String firstName;
    private String middleName;
    @TextIndexed String lastName;
    private String jobTitle;
    private String email;
    private Date birthDate;
    private BigDecimal salary;
    private Address address;
    private String twitterId;

    private @Version
    @JsonIgnore
    Long version;

    private Employee() {this.employeeStatus = EmployeeStatus.UNKNOWN;}


    public Employee(String firstName, String middleName, String lastName, String email, String jobTitle, BigDecimal salary, String twitter) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.employeeStatus = EmployeeStatus.ACTIVE;
        this.twitterId = twitter;
        this.salary = salary;
    }

    public Employee(String firstName, String middleName, String lastName, String email, String jobTitle, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.employeeStatus = EmployeeStatus.ACTIVE;
        this.salary = salary;
    }

    public Employee(String firstName, String middleName, String lastName, String email, String jobTitle, BigDecimal salary, EmployeeStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.employeeStatus = status;
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
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

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
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