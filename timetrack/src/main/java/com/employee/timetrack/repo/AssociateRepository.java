package com.employee.timetrack.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.timetrack.bean.Associate;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long>{
	public Associate findByUsername(String username);
}
