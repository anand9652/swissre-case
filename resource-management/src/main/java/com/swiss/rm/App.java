package com.swiss.rm;

import java.io.IOException;

/**
 * Anand *
 */
public class App 
{
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        try {
            manager.loadEmployeesFromCSV("src/main/resources/employees.csv");
            manager.analyzeSalariesAndReportingLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
