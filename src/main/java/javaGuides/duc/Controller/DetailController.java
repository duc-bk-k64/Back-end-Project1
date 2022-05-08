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

import io.swagger.annotations.ApiOperation;
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
	@ApiOperation("Only fill code,photo,time_in.")
	public ResponseEntity<String> createDetail(@RequestBody DetailDTO detailDTO) {
		String message = detailsService.createDetail(detailDTO);
		return ResponseEntity.ok().body(message);
	}

	@PostMapping("/end") // save comment and time out infomation
	@PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
	@ApiOperation("Only fill code, comment, time_out")
	public ResponseEntity<String> endDetail(@RequestBody DetailDTO detailDTO) {
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
