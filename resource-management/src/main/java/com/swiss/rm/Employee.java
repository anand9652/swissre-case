package com.swiss.rm;

public class Employee {
    
    // Unique ID for the employee
    private int id;
    
    // Employee's first name
    private String firstName;
    
    // Employee's last name
    private String lastName;
    
    // Employee's salary
    private double salary;
    
    // Manager's ID. This will be null if the employee is the CEO (has no manager)
    private Integer managerId;

    // Constructor to initialize an Employee object with all its attributes
    public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    // Getter for the employee ID
    public int getId() {
        return id;
    }

    // Getter for the employee's full name (concatenates first and last name)
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Getter for the employee's salary
    public double getSalary() {
        return salary;
    }

    // Getter for the employee's manager ID
    public Integer getManagerId() {
        return managerId;
    }
}
