package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javaGuides.duc.Entity.ClassRoom;
import javaGuides.duc.Entity.Menu;
import javaGuides.duc.Repository.studentRepository;
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
	public ResponseEntity<Map<String, String>> creaetClassroom(@RequestParam String name) {
		String message = classroomService.createClassroom(name);
		Map<String, String> map = new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, String>> updateClassroom(@RequestParam String oldName, @RequestParam String name) {
		String message = classroomService.updateClassroom(oldName, name);
		Map<String, String> map = new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/addteacher")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, String>> addTeacher(@RequestParam String name, @RequestParam String teacherCode) {
		String message = classroomService.addTeacher(name, teacherCode);
		Map<String, String> map = new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/addstudent")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, String>> addStudent(@RequestParam String name, @RequestParam String studentCode) {
		String message = classroomService.addStudent(name, studentCode);
		Map<String, String> map = new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/infor")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Map<String, String>>> getInformation(@RequestParam String name) {
		List<Map<String, String>> list = classroomService.getInformation(name);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Map<String, String>>> getAll() {
		return new ResponseEntity<List<Map<String, String>>>(classroomService.getall(), HttpStatus.OK);
	}

	@PostMapping("/addMenu/{id}")
	public ResponseEntity<Map<String, String>> addMenu(@PathVariable Long id, @RequestParam Long menuID) {
		String mesage = classroomService.addMenu(menuID, id);
		Map<String, String> map = new HashMap<>();
		map.put("Message", mesage);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/removeMenu/{id}")
	public ResponseEntity<Map<String, String>> removeMenu(@PathVariable Long id, @RequestParam Long menuID) {
		String mesage = classroomService.removeMenu(menuID, id);
		Map<String, String> map = new HashMap<>();
		map.put("Message", mesage);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/menu/{id}")
	public ResponseEntity<List<Map<String, String>>> getMenu(@PathVariable Long id) {
		Set<Menu> set = classroomService.getMenu(id);
		List<Map<String, String>> list = new ArrayList<>();
		set.forEach(menu -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(menu.getId()));
			map.put("Name", menu.getName());
			map.put("Comment", menu.getComment());
			map.put("Date", menu.getDate().toString());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);
	}

}
