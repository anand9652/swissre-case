package com.swiss.rm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeManagerTest {

    private EmployeeManager manager;

    @BeforeEach
    public void setUp() {
        manager = new EmployeeManager();
    }

    @Test
    public void testLoadEmployees() throws IOException {
        manager.loadEmployeesFromCSV("employees.csv");
        //assertEquals(5, manager.getEmployees().size());  // Adjust according to the number of employees
    }

    @Test
    public void testAnalyzeSalaries() throws IOException {
        manager.loadEmployeesFromCSV("employees.csv");
        // Check for correct output, adjust according to data
    }
}
