package com.security.config;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.entity.Student;
import com.security.reposiotry.StudentRepo;
@Service
/*this cls is for aunthenticating the user after user giving user&pwd while login*/
public class MyUserDetailasService implements UserDetailsService{

	@Autowired
	private StudentRepo studentRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Student>studentRes= studentRepo.findByStudentName(username);
		Student student = studentRes.get();
		if(studentRes.isPresent()) {
			return User.builder()
					.username(student.getStudentName())
					.password(student.getPassword())
					.roles(getRoles(student))
					.build();
		}
		else {
			throw new UsernameNotFoundException(username);
		}
	}


	private String[] getRoles(Student student) {
		if(student.getRole()==null) {
			return new String[]{"USER"};
		}
		return student.getRole().split(",");
	}

}
