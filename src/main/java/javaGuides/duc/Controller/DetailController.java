package javaGuides.duc.Controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import javaGuides.duc.DTO.DetailDTO;
import javaGuides.duc.Service.DetailsService;

@RestController
@RequestMapping("/api/v1/detail")
public class DetailController {
	private DetailsService detailsService;

	public DetailController(DetailsService detailsService) {
		this.detailsService = detailsService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> createDetail(@RequestParam String code, @RequestParam String photo) {
		DetailDTO detailDTO = new DetailDTO();
		detailDTO.setCode(code);
		detailDTO.setComment(null);
		detailDTO.setPhoto(photo);
		detailDTO.setTime_in(Instant.now());
		detailDTO.setTime_out(null);
		String message = detailsService.createDetail(detailDTO);
		return ResponseEntity.ok().body(message);
	}

	@PostMapping("/end") // save comment and time out infomation
	@PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> endDetail(@RequestParam String code, @RequestParam String comment) {
		DetailDTO detailDTO = new DetailDTO();

		detailDTO.setCode(code);
		detailDTO.setComment(comment);
		// detailDTO.setPhoto(photo);
		// detailDTO.setTime_in(Instant.now());
		detailDTO.setTime_out(Instant.now());
		String message = detailsService.endDetail(detailDTO);
		return ResponseEntity.ok().body(message);
	}

	@GetMapping("/all")
	public ResponseEntity<List<String>> getAll() {
		List<String> list = new ArrayList<>();
		detailsService.getAll().forEach(detail -> {
			list.add(detail.getDetail());
		});
		return ResponseEntity.ok().body(list);

	}

}
