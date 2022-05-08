package javaGuides.duc.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javaGuides.duc.Service.ClassroomService;

@RestController
@RequestMapping("/api/v1/classroom")
public class ClassroomCotroller {
	private ClassroomService classroomService;

	public ClassroomCotroller(ClassroomService classroomService) {
		this.classroomService = classroomService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> creaetClassroom(@RequestParam String name) {
		String message = classroomService.createClassroom(name);
		return ResponseEntity.ok().body(message);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> updateClassroom(@PathVariable Long id, @RequestParam String name) {
		String message = classroomService.updateClassroom(id, name);
		return ResponseEntity.ok().body(message);
	}

	@PostMapping("/addteacher/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> addTeacher(@PathVariable Long id, @RequestParam String teacherCode) {
		String message = classroomService.addTeacher(id, teacherCode);
		return ResponseEntity.ok().body(message);
	}

	@PostMapping("/addstudent/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> addStudent(@PathVariable Long id, @RequestParam String studentCode) {
		String message = classroomService.addStudent(id, studentCode);
		return ResponseEntity.ok().body(message);
	}

	@GetMapping("/infor/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<String>> getInformation(@PathVariable Long id) {
		List<String> list = classroomService.getInformation(id);
		return ResponseEntity.ok().body(list);
	}

}
