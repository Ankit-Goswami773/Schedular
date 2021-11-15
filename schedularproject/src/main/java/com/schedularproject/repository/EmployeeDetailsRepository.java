package com.schedularproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.schedularproject.entity.EmployeeDetails;

@Repository
public interface EmployeeDetailsRepository extends  JpaRepository<EmployeeDetails, Long>   {

	/*
	 * @Query("select e from EmployeeDetails  e where e.emailId =:emailId")
	 * EmployeeDetails getEmployeeByEmailId(String emailId);
	 */

}
