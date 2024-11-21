package com.swiss.rm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeManager {

    private Map<Integer, Employee> employees = new HashMap<>();
    private Map<Integer, List<Employee>> managerToSubordinates = new HashMap<>();
    private Employee ceo;

    public void loadEmployeesFromCSV(String csvFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))){
        	String line;
        	reader.readLine();//skip header
            while ((line = reader.readLine()) != null ) {
            	String parts[] =line.split(",");
            	if(parts.length == 1) continue;//skip blank/empty lines
                int id = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                double salary = Double.parseDouble(parts[3]);
                Integer managerId = null;
                if(parts.length > 4) {
                	managerId = parts[4].isEmpty() ? null : Integer.parseInt(parts[4]);
                }
                

                Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                employees.put(id, employee);

                if (managerId != null) {
                    managerToSubordinates.computeIfAbsent(managerId, k -> new ArrayList<>()).add(employee);
                } else {
                    ceo = employee;  // CEO has no manager
                }
            }
        }catch(IOException ex) {
        	System.out.println("Error occured while reading file "+ex.getMessage());
        }
        
    }

    public void analyzeSalariesAndReportingLines() {
        // 1. Report managers earning too little or too much
        for (Map.Entry<Integer, List<Employee>> entry : managerToSubordinates.entrySet()) {
            Integer managerId = entry.getKey();
            Employee manager = employees.get(managerId);
            List<Employee> subordinates = entry.getValue();

            double avgSubordinateSalary = subordinates.stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0);

            double minExpectedSalary = avgSubordinateSalary * 1.2;
            double maxExpectedSalary = avgSubordinateSalary * 1.5;

            if (manager.getSalary() < minExpectedSalary) {
                System.out.println(manager.getFullName() + " earns too little: " + (minExpectedSalary - manager.getSalary()));
            } else if (manager.getSalary() > maxExpectedSalary) {
                System.out.println(manager.getFullName() + " earns too much: " + (manager.getSalary() - maxExpectedSalary));
            }
        }

        // 2. Check reporting lines
        for (Employee employee : employees.values()) {
            if (employee != ceo) {
                int reportingLineLength = getReportingLineLength(employee);
                if (reportingLineLength > 4) {
                    System.out.println(employee.getFullName() + " has a reporting line too long: " + reportingLineLength);
                }
            }
        }
    }

    private int getReportingLineLength(Employee employee) {
        int length = 0;
        Integer managerId = employee.getManagerId();
        while (managerId != null) {
            length++;
            managerId = employees.get(managerId).getManagerId();
        }
        return length;
    }
}
