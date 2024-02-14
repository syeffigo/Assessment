package com.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.login.service.UserService;

@Controller
public class UserController {
   @Autowired
   public UserService userService;
   
   @PostMapping("/signup")
   public String createUser(@RequestParam("name") String name,
		                    @RequestParam("email") String email,
		                    @RequestParam("password") String password,
		                    @RequestParam("photo") MultipartFile photo,
		                    HttpSession session) {
	   System.out.println(name);
	   return userService.createUser(name, email, password, photo, session);
   }
   
   @PostMapping("/login")
   public String loginUser(@RequestParam("email") String email,
		               @RequestParam("password") String password,
		               HttpSession session) {
	   return userService.loginUser(email, password, session);
   }
   
   @GetMapping("/")
   public String login() {
	   return "login";
   }
   
   @GetMapping("/register")
   public String register() {
	   return "register";
   }
   
   @GetMapping("/home")
   public String home() {
	   return "home";
   }
}
