package javaGuides.duc.Controller;

import java.time.Instant;
import java.util.ArrayList;
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

import io.swagger.annotations.ApiOperation;
import javaGuides.duc.DTO.ActivityDTO;
import javaGuides.duc.Service.ActivityService;


@RestController
@RequestMapping("/api/v1/activity")
public class ActivityController {
	private ActivityService activityService;

	public ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> createActivity(@RequestParam String name, @RequestParam String detail,
			@RequestParam Instant time) {
		// Instant time=Instant.parse(time);
		ActivityDTO activityDTO = new ActivityDTO(detail, name, time);
		String message = activityService.create(activityDTO);
		return ResponseEntity.ok().body(message);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> updateActivity(@PathVariable Long id, @RequestParam String name,
			@RequestParam String detail, @RequestParam Instant time) {
		// Instant time=Instant.parse(time);
		ActivityDTO activityDTO = new ActivityDTO(detail, name, time);
		String message = activityService.update(id, activityDTO);
		return ResponseEntity.ok().body(message);
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("add account to activity")
	public ResponseEntity<String> add(@RequestParam Long id, @RequestParam String email) {
		String message = activityService.addAccount(id, email);
		return ResponseEntity.ok().body(message);
	}

	@GetMapping("/all")
	public ResponseEntity<List<String>> all() {
		List<String> list = new ArrayList<>();
		activityService.getAll().forEach(activity -> {
			String string = "ID:" + activity.getId() + " Name:" + activity.getName() + " Detail:" + activity.getDetail()
					+ " Time:" + activity.getTime();
			list.add(string);
		});
		return ResponseEntity.ok().body(list);
	}

}
