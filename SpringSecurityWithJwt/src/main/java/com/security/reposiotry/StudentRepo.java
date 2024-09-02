package com.security.reposiotry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.entity.Student;
@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

	
	Optional<Student>findByStudentName(String stName);
}
