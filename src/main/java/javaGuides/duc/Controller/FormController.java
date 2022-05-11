package javaGuides.duc.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javaGuides.duc.DTO.FormDTO;
import javaGuides.duc.Repository.studentRepository;
import javaGuides.duc.Service.FormService;

@RestController
@RequestMapping("/api/v1/form")
public class FormController {
	private FormService formService;

	public FormController(FormService formService) {
		this.formService = formService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<Map<String,String>> createForm(@RequestBody FormDTO formDTO) {
		String message = formService.createFormm(formDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Map<String, String>>> getAll() {
		List<Map<String, String>> list = new ArrayList<>();
		formService.getAll().forEach(form -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(form.getId()));
			map.put("Time", form.getTime().toString());
			map.put("Status", form.getStatus());
			map.put("Name", form.getName());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);
	}

}
