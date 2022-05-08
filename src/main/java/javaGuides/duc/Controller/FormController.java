package javaGuides.duc.Controller;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javaGuides.duc.DTO.FormDTO;
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
	public ResponseEntity<String> createForm(@RequestBody FormDTO formDTO) {

		System.out.println("Data:" + formDTO.getCode());
		String message = formService.createFormm(formDTO);
		return ResponseEntity.ok().body(message);
	}

	@GetMapping("/all")
	public ResponseEntity<List<String>> getAll() {
		List<String> list = new ArrayList<>();
		formService.getAll().forEach(form -> {
			list.add(form.getDetail());
		});
		return ResponseEntity.ok().body(list);
	}

}
