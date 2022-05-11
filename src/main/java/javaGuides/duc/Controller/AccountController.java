package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import javaGuides.duc.Entity.User;
import javaGuides.duc.Service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("delete account")
	@PostMapping("/delete")
	public ResponseEntity<Map<String,String>> delete(@RequestParam String email) {
		String string=accountService.remove(email);
		Map<String,String> map=new HashMap<>();
		map.put("Message", string);
		return ResponseEntity.ok().body(map);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("Remove relation between account and role. You must call this api before call /api/v1/account/delete")
	@PostMapping("/remove")
	public ResponseEntity<Map<String,String>> remove(@RequestParam String email) {
		String string=accountService.removeRelation(email);
		Map<String,String> map=new HashMap<>();
		map.put("Message", string);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/activity")
	public ResponseEntity<List<Map<String, String>>> activity() {
		List<Map<String, String>> list = new ArrayList<>();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = accountService.findByUsername(username);
		user.getActivities().forEach(activity -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(activity.getId()));
			map.put("Name", activity.getName());
			map.put("Detail", activity.getDetail());
			map.put("Time", activity.getTime().toString());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);
	}

}
