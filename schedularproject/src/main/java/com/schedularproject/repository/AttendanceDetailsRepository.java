package com.schedularproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schedularproject.entity.AttendanceDetails;


@Repository
public interface AttendanceDetailsRepository extends JpaRepository<AttendanceDetails, Long> {

}
