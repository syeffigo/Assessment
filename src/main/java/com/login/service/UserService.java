package com.login.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.login.model.User;
import com.login.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	private final String UPLOAD_DIR = "D:\\Images";
	
	public String createUser(String name, String email, String password, MultipartFile photo, HttpSession session) {
		
		boolean f = userRepo.existsByEmail(email);
		
		if(f) {
			session.setAttribute("msg", "Email Id already exists");
		}
		else {
		try {
		byte[] bytes = photo.getBytes();
		Path filePath = Paths.get(UPLOAD_DIR, photo.getOriginalFilename());
		Files.write(filePath, bytes);
		
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setFilePath(filePath.toString());
		
		userRepo.save(user);
		session.setAttribute("msg", "User Registered Successfuly");
		}catch(Exception e) {
			session.setAttribute("msg", "Some Error Occurred");
		}
		}
		return "redirect:/register";
	}
	
	public String loginUser(String email, String password, HttpSession session) {
		boolean f = userRepo.existsByEmail(email);
		System.out.println("papa");
		if(f) {
			User curr_user = (userRepo.findByEmail(email));
			String cur_password = (curr_user).getPassword();
			
			System.out.println(cur_password);
			if(cur_password.equals(password)) {
				session.setAttribute("msg", "Logged In Successfully.");
			}
			else {
				session.setAttribute("msg", "Invalid credentials");
			}
		}
		else {
			session.setAttribute("msg", "Invalid Credentials");
		}
		
		return "redirect:/home";
	}
}
