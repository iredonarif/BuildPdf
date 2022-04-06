package com.woomi.buildPdf.repositories;

import com.woomi.buildPdf.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
}
