package com.swiss.rm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeManager {

    // Logger for logging messages
    private static final Logger logger = Logger.getLogger(EmployeeManager.class.getName());

    // Map to store employees by their ID
    private Map<Integer, Employee> employees = new HashMap<>();
    
    // Map to store manager-subordinate relationships
    private Map<Integer, List<Employee>> managerToSubordinates = new HashMap<>();
    
    // CEO, who has no manager
    private Employee ceo;

    // Getters and setters for the fields, if needed for external access/modification
    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<Integer, Employee> employees) {
        this.employees = employees;
    }

    public Map<Integer, List<Employee>> getManagerToSubordinates() {
        return managerToSubordinates;
    }

    public void setManagerToSubordinates(Map<Integer, List<Employee>> managerToSubordinates) {
        this.managerToSubordinates = managerToSubordinates;
    }

    public Employee getCeo() {
        return ceo;
    }

    public void setCeo(Employee ceo) {
        this.ceo = ceo;
    }

    // Method to load employees from a CSV file (version 1)
    public void loadEmployeesFromCSV_v1(String csvFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            reader.readLine(); // Skip the header line

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 1) continue; // Skip empty lines

                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                double salary = Double.parseDouble(parts[3]);

                // Handle managerId, which could be null (CEO case)
                Integer managerId = null;
                if (parts.length > 4) {
                    managerId = parts[4].isEmpty() ? null : Integer.parseInt(parts[4]);
                }

                // Create employee and add to the employees map
                Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                employees.put(id, employee);

                // Add the employee to the manager's list of subordinates if applicable
                if (managerId != null) {
                    managerToSubordinates.computeIfAbsent(managerId, k -> new ArrayList<>()).add(employee);
                } else {
                    ceo = employee; // If no managerId, this is the CEO
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error occurred while reading file: " + ex.getMessage(), ex);
            throw ex;
        }
    }

    // Enhanced version of loadEmployeesFromCSV with error handling for malformed rows
    public void loadEmployeesFromCSV(String csvFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            reader.readLine(); // Skip the header line

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) {
                    // Skip lines with missing required columns (ID, firstName, lastName, and salary)
                    logger.warning("Skipping invalid row (missing required columns): " + line);
                    continue;
                }

                try {
                    // Parsing the CSV columns into appropriate types
                    int id = Integer.parseInt(parts[0]);
                    String firstName = parts[1];
                    String lastName = parts[2];
                    double salary = Double.parseDouble(parts[3]);
                    Integer managerId = null;

                    if (parts.length > 4) {
                        managerId = parts[4].isEmpty() ? null : Integer.parseInt(parts[4]);
                    }

                    // Create employee object and add it to the employees map
                    Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                    employees.put(id, employee);

                    // Associate the employee with their manager, or set them as the CEO
                    if (managerId != null) {
                        managerToSubordinates.computeIfAbsent(managerId, k -> new ArrayList<>()).add(employee);
                    } else {
                        ceo = employee; // If no manager, this is the CEO
                    }

                } catch (NumberFormatException e) {
                    // Skip any rows that cause a NumberFormatException (e.g., invalid salary or ID)
                    logger.warning("Skipping invalid row due to NumberFormatException: " + line);
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error occurred while reading file: " + ex.getMessage(), ex);
            throw ex; // Rethrow the exception for handling at a higher level if necessary
        }
    }

    // Method to analyze salaries and reporting lines for all managers and employees
    public void analyzeSalariesAndReportingLines() {
        // 1. Report managers earning too little or too much
        for (Map.Entry<Integer, List<Employee>> entry : managerToSubordinates.entrySet()) {
            Integer managerId = entry.getKey();
            Employee manager = employees.get(managerId);
            List<Employee> subordinates = entry.getValue();

            // Calculate the average salary of the subordinates
            double avgSubordinateSalary = subordinates.stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0);

            // Expected salary ranges for managers
            double minExpectedSalary = avgSubordinateSalary * 1.2;
            double maxExpectedSalary = avgSubordinateSalary * 1.5;

            // Check if the manager's salary is too low or too high compared to subordinates' average
            if (manager.getSalary() < minExpectedSalary) {
                logger.info(manager.getFullName() + " earns too little: " + (minExpectedSalary - manager.getSalary()));
            } else if (manager.getSalary() > maxExpectedSalary) {
                logger.info(manager.getFullName() + " earns too much: " + (manager.getSalary() - maxExpectedSalary));
            }
        }

        // 2. Check reporting lines and report if they are too long (more than 4 levels)
        for (Employee employee : employees.values()) {
            if (employee != ceo) {
                int reportingLineLength = getReportingLineLength(employee);
                if (reportingLineLength > 4) {
                    logger.info(employee.getFullName() + " has a reporting line too long: " + reportingLineLength);
                }
            }
        }
    }

    // Helper method to calculate the length of an employee's reporting line (chain of managers)
    public int getReportingLineLength(Employee employee) {
        int length = 0;
        Integer managerId = employee.getManagerId();
        
        // Traverse the reporting line until we reach the CEO (managerId becomes null)
        while (managerId != null) {
            length++;
            managerId = employees.get(managerId).getManagerId();
        }
        
        return length;
    }
}
