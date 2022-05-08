package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import javaGuides.duc.DTO.informationDTO;
import javaGuides.duc.Entity.ClassRoom;
import javaGuides.duc.Entity.Student;
import javaGuides.duc.Entity.User;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Service.AccountService;
import javaGuides.duc.Service.studentService;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
	private studentService service;
	private AccountService accountService;

	public StudentController(studentService service, AccountService accountService) {
		this.service = service;
		this.accountService = accountService;
	}

	@GetMapping("/getByCode")
	public ResponseEntity<String> getByCodStudent(@RequestParam String code) {
		try {
			Student student = service.findStudentByStudentCode(code);
			if (student == null)
				return ResponseEntity.ok().body("Student not found in system");
			return ResponseEntity.ok().body(student.getDetails());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<String>> getall() {
		List<String> list = new ArrayList<>();
		service.getAll().forEach(student -> {
			list.add(student.getDetails());
		});
		return ResponseEntity.ok().body(list);
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> createStudent(@RequestBody informationDTO data) {
		try {
			Student student = new Student();
			student.setAdress(data.getAdress());
			student.setDateOfBirth(data.getDateofBirth());
			student.setImage(data.getImage());
			student.setName(data.getName());
			student.setPhoneNumber(data.getPhoneNumber());
			student.setStudentCode(data.getCode());
			service.createStudent(student);
			return ResponseEntity.ok().body("create student successfully. StudentID:" + data.getCode());

		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> updateStudent(@RequestBody informationDTO data) {
		try {
			service.updateStudent(data);
			return ResponseEntity.ok().body("update successfully");
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> deleteStudent(@RequestParam String code) {
		try {

			return ResponseEntity.ok().body(service.deleteStudent(code));
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("Make relation betwwen account and student")
	public ResponseEntity<String> add(@RequestParam String username, @RequestParam String code) {
		User user = accountService.findByUsername(username);
		Student student = service.findStudentByStudentCode(code);
		if (user == null || student == null)
			return ResponseEntity.ok().body("Not found information");
		student.setUser(user);
		service.createStudent(student);
		return ResponseEntity.ok().body("successfully");

	}

	@PutMapping("/remove")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("Remove relation between account and student. you must call this api before call /api/v1/student/delete")
	public ResponseEntity<String> remove(@RequestParam String code) {
		try {
			return ResponseEntity.ok().body(service.removeRelation(code));
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@GetMapping("/personalInfor")
	public ResponseEntity<String> getPersonalInformation() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = accountService.findByUsername(username);
		if (user.getStudent() == null)
			return ResponseEntity.ok().body("Not found information");
		return ResponseEntity.ok().body(user.getStudent().getDetails());
	}

	@GetMapping("/detail")
	public ResponseEntity<String> getDetail(@RequestParam String code) {
		try {
			Student student = service.findStudentByStudentCode(code);
			if (student == null)
				return ResponseEntity.ok().body("Student not found in system");
			return ResponseEntity.ok().body(student.getDetail().getDetail());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	@GetMapping("/form")
	public ResponseEntity<List<String>> getForm(@RequestParam String code) {
		List<String> list = new ArrayList<>();
		Student student = service.findStudentByStudentCode(code);

		if (student == null) {
			list.add("Student not found in system");
			return ResponseEntity.ok().body(list);
		}
		student.getForms().forEach(form -> {
			list.add(form.getDetail());
		});
		return ResponseEntity.ok().body(list);

	}

	@GetMapping("/classroom")
	public ResponseEntity<String> getClassroom(@RequestParam String code) {
		Student student = service.findStudentByStudentCode(code);
		if (student == null)
			return ResponseEntity.ok().body("Student not found in system");
		ClassRoom classRoom = student.getClassRoom();
		if (classRoom == null)
			return ResponseEntity.ok().body("Student has'nt classroom");
		String string = "ID:" + classRoom.getId() + " Name:" + classRoom.getName();
		return ResponseEntity.ok().body(string);
	}

}
