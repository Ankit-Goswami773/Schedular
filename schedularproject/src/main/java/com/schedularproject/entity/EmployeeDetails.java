package com.schedularproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDetails {
	
	private long empId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String designation;
	private String department;
	private double basicSalary;
	private String status ;

}