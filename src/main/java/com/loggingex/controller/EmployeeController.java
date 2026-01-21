package com.loggingex.controller;

import com.loggingex.entity.Employee;
import com.loggingex.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private static final Logger logger= LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }

    @PostMapping("/save")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee)
    {
        logger.info("API called: Create Employee");
       Employee emp= employeeService.createEmployee(employee);
       return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
    {
        logger.info("API called: Get Employee by ID");
      Employee emp= employeeService.getEmployeeById(id);
      return new ResponseEntity<>(emp,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getAllEmployee()
    {
        logger.info("API called: GetAllEmployee");
       List<Employee> employeeList= employeeService.getAllEmployee();

       return new ResponseEntity<>(employeeList,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employee)
    {
        logger.info("API called: Update Employee");
       Employee updatedEmployee= employeeService.updateEmployee(id,employee);

       return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id)
    {
        logger.info("API called: Delete Employee");
        employeeService.deleteEmployee(id);

        return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);

    }
    //localhost:8085/api/v1/employees/page?pageNo=0&pageSize=3&sortBy=name&direction=asc
    @GetMapping("/page")
    public ResponseEntity<Page<Employee> >getEmployeeInPage(
          @RequestParam(defaultValue = "0",required = false)  int pageNo,
           @RequestParam(defaultValue = "3",required = false) int pageSize,
          @RequestParam(defaultValue = "id") String sortBy,
          @RequestParam(defaultValue = "asc")String direction)
    {
        logger.info("API called: Get employees with pagination");
      Page<Employee> emp= employeeService.getEmployeeWithPagination(pageNo,pageSize,sortBy,direction);
      return new ResponseEntity<>(emp,HttpStatus.OK);
    }


}
