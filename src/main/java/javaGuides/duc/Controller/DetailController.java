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
	public ResponseEntity<Map<String,String>> createDetail(@RequestBody DetailDTO detailDTO) {
		String message = detailsService.createDetail(detailDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/end") // save comment and time out infomation
	@PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
	@ApiOperation("Only fill code, comment, time_out")
	public ResponseEntity<Map<String,String>> endDetail(@RequestBody DetailDTO detailDTO) {
		String message = detailsService.endDetail(detailDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Map<String, String>>> getAll() {
		List<Map<String, String>> list = new ArrayList<>();
		detailsService.getAll().forEach(detail -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(detail.getId()));
			map.put("PhotoURL", detail.getPhoto());
			map.put("Time_in", detail.getTimeIn().toString());
			map.put("Time_out", detail.getTimeOut().toString());
			map.put("Comment", detail.getComment());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);

	}

}
