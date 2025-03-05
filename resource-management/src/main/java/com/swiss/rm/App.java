package com.swiss.rm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    
    // Logger for logging any exceptions or important information
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        // Create an instance of EmployeeManager to handle employee data
        EmployeeManager manager = new EmployeeManager();

        try {
            // Load employees from the CSV file located at src/main/resources/employees.csv
            // This will populate the employees map and manager-subordinate relationships
            manager.loadEmployeesFromCSV("src/main/resources/employees.csv");

            // After loading employees, perform the analysis of salaries and reporting lines
            // It checks if managers earn too little or too much and also checks reporting line lengths
            manager.analyzeSalariesAndReportingLines();

        } catch (IOException e) {
            // Log any IOException that occurs while reading the CSV or analyzing the data
            // This ensures the program handles file reading errors gracefully
            logger.log(Level.SEVERE, "An error occurred while loading or analyzing the employee data.", e);
        }
    }
}
