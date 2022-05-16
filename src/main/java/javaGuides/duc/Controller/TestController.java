package javaGuides.duc.Controller;

import java.util.ArrayList;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import javaGuides.duc.Entity.User;
import javaGuides.duc.Service.AccountService;

@RestController
@RequestMapping("/test")
public class TestController {
	private AccountService accountService;

	public TestController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/admin")
	@ApiOperation("resources for admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> getall() {
		return ResponseEntity.ok().body("role admin can access");
	}

	@GetMapping("/student")
	@ApiOperation("resources for student")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<String> auth() {
		return ResponseEntity.ok().body("role student can access");
	}

	@GetMapping("/teacher")
	@ApiOperation("resources for teacher")
	@PreAuthorize("hasRole('ROLE_TEACHER')")
	public ResponseEntity<String> auser() {
		return ResponseEntity.ok().body("Role admin or user can access");
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<List<String>> getAll() {
		List<String> user = new ArrayList<>();
		accountService.getAllUsers().forEach(account -> {
			user.add(account.getDetail(account));
		});
		return ResponseEntity.ok().body(user);
	}

	@GetMapping("/studentcode")
	@ApiOperation("Get username from token")
	public ResponseEntity<String> getUsername() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = accountService.findByUsername(username);
		if (user.getStudent() != null)
			return ResponseEntity.ok().body(user.getStudent().getStudentCode());
		else
			return ResponseEntity.ok().body("User name has'nt student");
	}

}
