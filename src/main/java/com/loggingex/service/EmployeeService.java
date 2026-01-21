package com.loggingex.service;

import com.loggingex.entity.Employee;
import com.loggingex.exception.ResourceNotFoundException;
import com.loggingex.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger= LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository=employeeRepository;
    }

    //CREATE
   public Employee createEmployee(Employee employee)
   {
     logger.info("Creating employee with name:{}",employee.getName());
     Employee savedEmployee=  employeeRepository.save(employee);

     logger.debug("Employee created with ID:{}",savedEmployee.getId());
     return savedEmployee;

   }

   //READ BY ID

    public Employee getEmployeeById(Long id)
    {
     logger.info("Fetching employee with ID:{}",id);
     Employee emp= employeeRepository.findById(id)
             .orElseThrow(()->new ResourceNotFoundException("Employee not found"));
      return emp;


    }


    //READ ALL

    public List<Employee> getAllEmployee()
    {
        logger.info("Fetching all employees");
      List<Employee> empList= employeeRepository.findAll();
      return empList;
    }


    //UPDATE
    public Employee updateEmployee(Long id,Employee newEmp){
        logger.info("Updating employee with ID:{}",id);
        Employee existingEmployee=getEmployeeById(id);

        existingEmployee.setName(newEmp.getName());
        existingEmployee.setDepartment(newEmp.getDepartment());
        existingEmployee.setSalary(newEmp.getSalary());

      logger.debug("Employee updated data:{}",existingEmployee);
      Employee updatedEmployee=  employeeRepository.save(existingEmployee);
      return updatedEmployee;

    }

    //DELETE
    public void deleteEmployee(Long id)
    {
        logger.warn("Deleting employee with ID:{}",id);
       Employee emp=getEmployeeById(id);
       employeeRepository.delete(emp);
       logger.info("Employee deleted successfully with ID:{}",id);
    }

    //Pagination

    public Page<Employee> getEmployeeWithPagination(int pageNo,int pageSize,String sortBy,String direction)
    {
        logger.info("Fetching employees, PageNo:{},PageSize:{},SortBy:{},Direction:{}",pageNo,pageSize,sortBy,direction);
        Sort sort=direction.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

      Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
      return employeeRepository.findAll(pageable);
    }

}
