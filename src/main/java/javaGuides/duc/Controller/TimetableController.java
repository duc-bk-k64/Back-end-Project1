package javaGuides.duc.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import javaGuides.duc.DTO.TimetableDTO;
import javaGuides.duc.Service.TimetableService;

@RestController
@RequestMapping("/api/v1/timetable")
public class TimetableController {
	private TimetableService timetableService;
	public TimetableController(TimetableService timetableService) {
		this.timetableService = timetableService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> createTimeTable(@RequestBody TimetableDTO timetableDTO) {
		String message = timetableService.createTimetable(timetableDTO);
		return ResponseEntity.ok().body(message);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> updateTimeTable(@PathVariable Long id, @RequestBody TimetableDTO timetableDTO) {
		String message = timetableService.updateTimetable(id, timetableDTO);
		return ResponseEntity.ok().body(message);
	}

	@GetMapping("/all")
	public ResponseEntity<List<String>> getAll() {
		return ResponseEntity.ok().body(timetableService.getAll());
	}

	@PostMapping("/add/{id}")
	@ApiOperation("add list student for timetable")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> addStudent(@PathVariable Long id, @RequestBody Set<String> code) {
		return ResponseEntity.ok().body(timetableService.addStudent(code, id));
	}

}
