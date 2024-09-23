package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.entity.Student;
import com.security.reposiotry.StudentRepo;

@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepo;
	
	public String saveData(Student student) {
		Student stData = studentRepo.save(student); 
		return "saved..";
	}
	
	
}
