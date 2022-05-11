package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javaGuides.duc.Entity.Detail;
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
	@ApiOperation("If Respon=0 then get MessageError, else get information")
	public ResponseEntity<Map<String,String>> getByCodStudent(@RequestParam String code) {
		try {
			Map<String,String> map=new HashMap<>();
			Student student = service.findStudentByStudentCode(code);
			if (student == null) {
				map.put("Respon",String.valueOf(0));
				map.put("MessageError","Student not found in system");
				return ResponseEntity.ok().body(map);
			}
			map.put("Respon",String.valueOf(1));
			map.put("Student_code",student.getStudentCode());
			map.put("Adress",student.getAdress());
			map.put("Name",student.getName());
			map.put("ImageURL",student.getImage());
			map.put("PhoneNumber", student.getPhoneNumber());
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Map<String,String>>> getall() {
		List<Map<String,String>> list = new ArrayList<>();
		service.getAll().forEach(student -> {
			Map<String,String> map=new HashMap<>();
			map.put("Student_code",student.getStudentCode());
			map.put("Adress",student.getAdress());
			map.put("Name",student.getName());
			map.put("ImageURL",student.getImage());
			map.put("PhoneNumber", student.getPhoneNumber());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String,String>> createStudent(@RequestBody informationDTO data) {
		try {
			Student student = new Student();
			student.setAdress(data.getAdress());
			student.setDateOfBirth(data.getDateofBirth());
			student.setImage(data.getImage());
			student.setName(data.getName());
			student.setPhoneNumber(data.getPhoneNumber());
			student.setStudentCode(data.getCode());
			service.createStudent(student);
			Map<String,String> map=new HashMap<>();
			map.put("Message","create student successfully. StudentID:" + data.getCode());
			return ResponseEntity.ok().body(map);

		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String,String>> updateStudent(@RequestBody informationDTO data) {
		try {
			String message= service.updateStudent(data);
			Map<String,String> map=new HashMap<>();
			map.put("Message", message);
			return ResponseEntity.ok().body(map);
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
	public ResponseEntity<Map<String,String>> add(@RequestParam String username, @RequestParam String code) {
		User user = accountService.findByUsername(username);
		Student student = service.findStudentByStudentCode(code);
		Map<String,String> map=new HashMap<>();
		if (user == null || student == null) {
			map.put("Message","Not found information");
			return ResponseEntity.ok().body(map);
		}
		
		student.setUser(user);
		service.createStudent(student);
		map.put("Message","Successfully");
		return ResponseEntity.ok().body(map);

	}

	@PutMapping("/remove")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("Remove relation between account and student. you must call this api before call /api/v1/student/delete")
	public ResponseEntity<Map<String,String>> remove(@RequestParam String code) {
		try {
			Map<String,String> map=new  HashMap<>();
			map.put("Message",service.removeRelation(code));
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@GetMapping("/personalInfor")
	public ResponseEntity<Map<String,String>> getPersonalInformation() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = accountService.findByUsername(username);
		Student student=user.getStudent();
		Map<String,String> map=new HashMap<>();
		if (student == null) {
			map.put("Respon",String.valueOf(0));
			map.put("MessageError","Student not found in system");
			return ResponseEntity.ok().body(map);
		}
		map.put("Respon",String.valueOf(1));
		map.put("Student_code",student.getStudentCode());
		map.put("Adress",student.getAdress());
		map.put("Name",student.getName());
		map.put("ImageURL",student.getImage());
		map.put("PhoneNumber", student.getPhoneNumber());
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/detail")
	public ResponseEntity<Map<String,String>> getDetail(@RequestParam String code) {
		try {
			Student student = service.findStudentByStudentCode(code);
			Map<String,String> map=new HashMap<>();
			if (student == null) {
				map.put("Respon",String.valueOf(0));
				map.put("MessageError","Student not found in system");
				return ResponseEntity.ok().body(map);
			}
			Detail detail=student.getDetail();
			map.put("Respon",String.valueOf(1));
			map.put("ID", String.valueOf(detail.getId()));
			map.put("PhotoURL", detail.getPhoto());
			map.put("Time_in", detail.getTimeIn().toString());
			map.put("Time_out", detail.getTimeOut().toString());
			map.put("Comment", detail.getComment());
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	@GetMapping("/form")
	public ResponseEntity<List<Map<String,String>>> getForm(@RequestParam String code) {
		Student student = service.findStudentByStudentCode(code);
        List<Map<String,String>> list=new ArrayList<>();
		if (student == null) {
			Map<String,String> map=new HashMap<>();
			map.put("Respon",String.valueOf(0));
			map.put("MessageError","Student not found in system");
			list.add(map);
			return ResponseEntity.ok().body(list);
		}
		student.getForms().forEach(form -> {
			Map<String,String> map=new HashMap<>();
			map.put("Respon",String.valueOf(1));
			map.put("ID", String.valueOf(form.getId()));
			map.put("Time", form.getTime().toString());
			map.put("Status", form.getStatus());
			map.put("Name", form.getName());
			list.add(map);
			
		});
		return ResponseEntity.ok().body(list);

	}

	@GetMapping("/classroom")
	public ResponseEntity<Map<String,String>> getClassroom(@RequestParam String code) {
		Student student = service.findStudentByStudentCode(code);
		Map<String,String> map=new HashMap<>();
		if (student == null) {
			map.put("Message","Student not found in system");
		    map.put("Respon",String.valueOf(0));
			return ResponseEntity.ok().body(map);
		}
		ClassRoom classRoom = student.getClassRoom();
		if (classRoom == null) {
			map.put("Message","Student has'nt classroom");
		    map.put("Respon",String.valueOf(0));
			return ResponseEntity.ok().body(map);
		}
		map.put("Respon",String.valueOf(1));
		map.put("ID",String.valueOf(classRoom.getId()));
		map.put("Name",classRoom.getName());
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/timetable")
	public ResponseEntity<List<String>> getTimeTable(@RequestParam String code) {
		Student student = service.findStudentByStudentCode(code);
		ArrayList<String> list = new ArrayList<>();
		if (student == null) {
			list.add("Student not found in system");
			return ResponseEntity.ok().body(list);
		}
		if (student.getTables() == null) {
			list.add("Student has'nt timetable");
			return ResponseEntity.ok().body(list);
		}
		student.getTables().forEach(timetable -> {
			String string = "ID:" + timetable.getId() + " Detail:" + timetable.getDetail() + " Time:"
					+ timetable.getTime();
			list.add(string);
		});
		return ResponseEntity.ok().body(list);
	}

}
