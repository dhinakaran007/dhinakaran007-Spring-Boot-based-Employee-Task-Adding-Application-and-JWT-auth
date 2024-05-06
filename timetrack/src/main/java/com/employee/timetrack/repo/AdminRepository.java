package com.employee.timetrack.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.timetrack.bean.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
