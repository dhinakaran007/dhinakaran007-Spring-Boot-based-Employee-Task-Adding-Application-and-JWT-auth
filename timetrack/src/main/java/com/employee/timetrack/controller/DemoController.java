package com.employee.timetrack.controller;

import java.sql.Date;
import java.util.List;

import com.employee.timetrack.exception.EditTaskValidationException;
import com.employee.timetrack.exception.TaskValidationException;
import com.employee.timetrack.model.JwtRequest;
import com.employee.timetrack.repo.TaskRepository;
import com.employee.timetrack.service.CustomUserDetailsService;
import com.employee.timetrack.service.ReportService;
import com.employee.timetrack.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.employee.timetrack.bean.Admin;
import com.employee.timetrack.bean.Task;
import com.employee.timetrack.repo.AdminRepository;
import com.employee.timetrack.service.LoginService;
import com.employee.timetrack.service.TaskService;

@RestController
public class DemoController {
	
	@Autowired
	AdminRepository adminRepository;
	LoginService loginService;

	@Autowired
	TaskService taskService;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	private ReportService reportService;

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/home")
	public String home(){
		return "redirect:/api/addtask";
	}

	@GetMapping("/admin")
	public String admin() {
		return "This is admin";
	}


	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getUsername(),
							jwtRequest.getPassword()
					)
			);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		final UserDetails userDetails
				= userDetailsService.loadUserByUsername(jwtRequest.getUsername());

		final String token =
				jwtUtility.generateToken(userDetails);

		return  new JwtResponse(token);
	}

	@PostMapping("/api/addtask")
	public ResponseEntity<String> addTask(@RequestBody Task task) {
		try{
			Task savedTask = taskService.saveTask(task);
			return new ResponseEntity<>("Task Successfully added with ID: " + savedTask.getTaskId(), HttpStatus.CREATED);
		} catch (TaskValidationException e) {
			return new ResponseEntity<>("Failed to add the Task " +e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/api/updatetask")
	public ResponseEntity<String> updateTask(@RequestBody Task task) {
		try{
			Task updateTask = taskService.updateTask(task);
			return new ResponseEntity<>("Task Updated Successfully with ID: " + updateTask.getTaskId(), HttpStatus.OK);
		} catch(EditTaskValidationException e) {
			return new ResponseEntity<>("Failed to update the task " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/api/deletetask/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable Long id) {
		try{
			if(taskRepository.existsById(id)) {
				taskRepository.deleteById(id);
			}
			return new ResponseEntity<>("Task deleted Successfully ",  HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete the task ", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/api/report/daily/{date}")
	public ResponseEntity<?> getDailyReport(@PathVariable Date date) {
		List<Task> dailyReport = reportService.generateDailyReport(date);
		return new ResponseEntity<>(dailyReport.toString(), HttpStatus.OK);
	}

	@GetMapping("/api/report/weekly/{startDate}/{endDate}")
	public ResponseEntity<?> generateWeeklyReport(@PathVariable Date startDate,
														   @PathVariable Date endDate) {
		List<Task> weeklyReport = reportService.generateWeeklyReport(startDate, endDate);
		return new ResponseEntity<>(weeklyReport.toString(), HttpStatus.OK);
	}

	@GetMapping("/api/report/monthly/{startDate}/{endDate}/{associateId}")
	public ResponseEntity<?> generateMonthlyReport(@PathVariable Date startDate,
															@PathVariable Date endDate,
															@PathVariable Long associateId) {
		List<Task> monthlyReport = reportService.generateMonthlyReport(startDate, endDate, associateId);
		return new ResponseEntity<>(monthlyReport.toString(), HttpStatus.OK);
	}



//	@GetMapping("/home")
//	public ModelAndView demo() {
//		ModelAndView modelAndView = new ModelAndView();
//		System.out.println("In show");
//		modelAndView.setViewName("detective");
//		return modelAndView;
//	}
//
//	@GetMapping("/getAdmins")
//	public List<Admin> getAdmin() {
//		return adminRepository.findAll();
//	}
//
//	@PostMapping("/assLogin")
//	public ModelAndView assLogin() {
//		ModelAndView modelAndView = new ModelAndView();
//		System.out.println("In show");
//		modelAndView.setViewName("associatelogin");
//		return modelAndView;
//	}
//
//	@PostMapping("/addTask")
//	public ModelAndView addtask() {
//		ModelAndView modelAndView = new ModelAndView();
//		System.out.println("In show");
//		modelAndView.setViewName("addtask");
//		return modelAndView;
//	}

}
