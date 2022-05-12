package javaGuides.duc.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Map<String,String>> createActivity(@RequestBody ActivityDTO activityDTO) {
		// Instant time=Instant.parse(time);
		String message = activityService.create(activityDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String,String>> updateActivity(@RequestBody ActivityDTO activityDTO) {
		String message = activityService.update(activityDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("add account to activity")
	public ResponseEntity<Map<String,String>> add(@RequestParam String code, @RequestParam String email) {
		String message = activityService.addAccount(code, email);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Map<String, String>>> all() {
		List<Map<String, String>> list = new ArrayList<>();
		activityService.getAll().forEach(activity -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(activity.getId()));
			map.put("Name", activity.getName());
			map.put("Detail", activity.getDetail());
			map.put("Time", activity.getTime().toString());
			map.put("Code",activity.getCode());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);
	}

}
