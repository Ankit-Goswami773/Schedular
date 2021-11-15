package com.schedularproject.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDetails {
	
	private long attendanceId;
	private LocalDate date;
	private boolean attendance;
	private EmployeeDetails employeeDetails;

}