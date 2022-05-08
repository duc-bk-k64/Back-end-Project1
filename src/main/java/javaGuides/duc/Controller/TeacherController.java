package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

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
import javaGuides.duc.Entity.teacher;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Service.AccountService;
import javaGuides.duc.Service.teacherService;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {
	private teacherService teacherService;
	private AccountService accountService;

	public TeacherController(teacherService teacherService, AccountService accountService) {
		this.teacherService = teacherService;
		this.accountService = accountService;
	}

	@GetMapping("/")
	@ApiOperation("Get all teacher")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<String>> getAll() {
		List<String> list = new ArrayList<>();
		teacherService.getAll().forEach(teacher -> {
			list.add(teacher.getDetail());
		});
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/getByCode")
	@ApiOperation("get student by teacher code")
	public ResponseEntity<String> getBycode(@RequestParam String code) {
		try {
			teacher teacher = teacherService.findByTeacherCode(code);
			if (teacherService == null)
				return ResponseEntity.ok().body("Not found in system");
			return ResponseEntity.ok().body(teacher.getDetail());
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@PostMapping("/create")
	@ApiOperation("Create teacher information")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> createTeacher(@RequestBody informationDTO data) {
		try {
			if (data == null)
				return ResponseEntity.ok().body("data is null");
			teacher teacher = new teacher();
			teacher.setAdress(data.getAdress());
			teacher.setDateOfBirth(data.getDateofBirth());
			teacher.setImage(data.getImage());
			teacher.setName(data.getName());
			teacher.setPhoneNumber(data.getPhoneNumber());
			teacher.setTeacherCode(data.getCode());
			teacherService.createTeacher(teacher);
			return ResponseEntity.ok().body("create teacher successfully");
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> updateTeacherInformation(@RequestBody informationDTO dto) {
		try {
			String string = teacherService.updateTeacher(dto);
			return ResponseEntity.ok().body(string);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("Make relation between account and teacher")
	public ResponseEntity<String> add(@RequestParam String username, @RequestParam String code) {
		teacher teacher = teacherService.findByTeacherCode(code);
		User user = accountService.findByUsername(username);
		if (teacher == null || user == null)
			return ResponseEntity.ok().body("Not found information");
		teacher.setUser(user);
		teacherService.createTeacher(teacher);
		return ResponseEntity.ok().body("Successfully");
	}

	@PostMapping("/remove")
	@ApiOperation("Remove relation between account and student. You must call this api before call delete api")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> remove(@RequestParam String code) {
		teacher teacher = teacherService.findByTeacherCode(code);
		if (teacher == null)
			return ResponseEntity.ok().body("Not found information");
		teacher.setUser(null);
		teacherService.createTeacher(teacher);//save change
		return ResponseEntity.ok().body("Successfully");
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> delete(String code) {
		try {
			String message = teacherService.deleteTeacher(code);
			return ResponseEntity.ok().body(message);

		} catch (Exception e) {
			throw new BadRequestException("You must call remove api before call this api");
		}
	}

	@GetMapping("/personalInfor")

	public ResponseEntity<String> getPersonalInformation() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = accountService.findByUsername(username);
		if (user.getTeacher() == null)
			return ResponseEntity.ok().body("Not found information");
		return ResponseEntity.ok().body(user.getTeacher().getDetail());
	}
	@GetMapping("/classroom")
	public ResponseEntity<String> getClassroom(@RequestParam String code) {
		teacher student = teacherService.findByTeacherCode(code);
		if (student == null)
			return ResponseEntity.ok().body("Teacher not found in system");
		ClassRoom classRoom = student.getClassRoom();
		if (classRoom == null)
			return ResponseEntity.ok().body("Teacher has'nt classroom");
		String string = "ID:" + classRoom.getId() + " Name:" + classRoom.getName();
		return ResponseEntity.ok().body(string);
	}
}
