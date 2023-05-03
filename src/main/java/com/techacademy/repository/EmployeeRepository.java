package com.techacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techacademy.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

    Optional<Employee> getById(int id);

    Optional<Employee> findById(int id);

    void deleteById(Integer id);

}
