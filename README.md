# swissre-case



Employee Management System
Overview
The Employee Management System is a Java application designed to manage employee records and analyze organizational structures. The core functionalities include loading employee data from a CSV file, analyzing salaries of managers, and checking the reporting structure for potential inefficiencies.
This system is composed of:
1. EmployeeManager Class: Handles employee data processing, analysis, and reporting.
2. Employee Class: Represents an employee with their personal information and relationships with other employees (manager-subordinate).
3. App Class: The entry point of the application, which loads employee data and triggers analysis.

Features
1. Loading Employee Data
The system loads employee data from a CSV file, which should contain employee information such as:
* Employee ID
* First Name
* Last Name
* Salary
* Manager's Employee ID (optional; null for CEO)
2. Analyzing Salaries and Reporting Lines
Once employees are loaded into the system, the application can analyze:
* Manager Salary Validation: Check if a manager's salary is reasonable based on the average salary of their subordinates.
* Reporting Line Length: Identify employees with excessively long reporting lines (more than 4 levels).
3. Logging and Error Handling
The system uses logging (via java.util.logging.Logger) to provide detailed information about:
* Errors when reading the CSV file.
* Invalid or incomplete rows in the CSV file.
* Salary and reporting line analysis results.

Project Structure
The project contains the following key files:
* EmployeeManager.java: Manages employee data and performs analysis.
* Employee.java: Represents an employee with personal and relational information.
* App.java: Main entry point to run the application.
* employees.csv: Sample CSV file to load employee data.

Requirements
* Java Version: The application is built using Java 8 or later.
* External Libraries: No external libraries are required for this project.

How to Run the Application
1. Prepare the CSV File
Ensure you have an employees.csv file structured as follows:
EmployeeIDFirstNameLastNameSalaryManagerID1JohnDoe600002JaneSmith5000013BobJohnson450001* The ManagerID is optional for the CEO (top-most employee), and if absent or empty, it should be set to null in the CSV.
2. Run the Application
* Compile the Java classes (if not using an IDE like IntelliJ or Eclipse).
* Run the App.java file as a Java application.
* The application will load employees from the specified CSV file (src/main/resources/employees.csv) and will analyze salaries and reporting lines.
javac *.java
java App
3. Output and Logging
The analysis results (salary validation and reporting line checks) will be printed in the console. In case of errors or invalid rows, warnings will be logged. You can track these logs for debugging or identifying any issues with the input data.

Classes Overview
Employee Class
Represents an individual employee.
Attributes:
* id (int): Unique identifier for the employee.
* firstName (String): The first name of the employee.
* lastName (String): The last name of the employee.
* salary (double): The salary of the employee.
* managerId (Integer): The manager's employee ID (null if the employee is the CEO).
Methods:
* getId(): Returns the employee's unique ID.
* getFullName(): Returns the full name (first and last name) of the employee.
* getSalary(): Returns the employee's salary.
* getManagerId(): Returns the employee's manager's ID.
EmployeeManager Class
Handles all operations related to employees, including loading data from CSV and performing analyses.
Methods:
* loadEmployeesFromCSV_v1(String csvFilePath): Loads employees from the given CSV file (version 1, simpler without error handling).
* loadEmployeesFromCSV(String csvFilePath): Loads employees from the CSV file with error handling (invalid rows are skipped and logged).
* analyzeSalariesAndReportingLines(): Analyzes the salary of managers relative to their subordinates and checks reporting line lengths.
* getReportingLineLength(Employee employee): Returns the number of levels in an employee’s reporting line.
App Class
The entry point of the application. It loads employee data from the CSV file and triggers the analysis.
Methods:
* main(String[] args): The main method that runs the application. It reads the CSV file, loads the employees, and calls the analysis methods.

Example Output
Sample CSV Input (employees.csv):
EmployeeID,FirstName,LastName,Salary,ManagerID
1,John,Doe,60000,
2,Jane,Smith,50000,1
3,Bob,Johnson,45000,1
4,Susan,Lee,47000,2
5,Tom,Hanks,40000,2
Sample Console Output:
INFO: John Doe earns too little: 10000.0
INFO: Jane Smith has a reporting line too long: 5
INFO: Susan Lee earns too little: 5400.0
Logging Example:
* Invalid row skipped due to missing required fields:
WARNING: Skipping invalid row (missing required columns): 123,John,,60000,
* NumberFormatException warning:
WARNING: Skipping invalid row due to NumberFormatException: 456,Jane,Smith,ABC
* Error in file reading:
SEVERE: Error occurred while reading file: FileNotFoundException

Troubleshooting
Problem: Employee data isn't being loaded properly.
* Solution: Ensure the CSV file is formatted correctly (with the correct number of columns and valid values). Invalid or incomplete rows will be logged as warnings.
Problem: The application throws an exception while reading the file.
* Solution: Check the file path provided to the loadEmployeesFromCSV() method. Ensure that the file exists and is accessible.
Problem: The program throws a NumberFormatException when parsing a CSV row.
* Solution: This indicates that the data in the CSV is improperly formatted. Check if all numeric values (e.g., Salary, Employee ID) are valid numbers.

Future Improvements
* User Interface (UI): Implement a GUI for more interactive user experience.
* Database Integration: Integrate the system with a database to handle large datasets and provide advanced search and reporting features.
* Advanced Reporting: Implement more complex reporting features, such as reporting on team performance or salary distributions.




