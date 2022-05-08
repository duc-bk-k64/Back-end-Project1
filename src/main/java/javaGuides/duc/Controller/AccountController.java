package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.List;

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
	public ResponseEntity<String> delete(@RequestParam String email) {
		return ResponseEntity.ok().body(accountService.remove(email));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation("Remove relation between account and role. You must call this api before call /api/v1/account/delete")
	@PostMapping("/remove")
	public ResponseEntity<String> remove(@RequestParam String email) {
		return ResponseEntity.ok().body(accountService.removeRelation(email));
	}

	@GetMapping("/activity")
	public ResponseEntity<List<String>> activity() {
		List<String> list = new ArrayList<>();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = accountService.findByUsername(username);
		user.getActivities().forEach(activity -> {
			String string = "ID:" + activity.getId() + " Name:" + activity.getName() + " Detail:" + activity.getDetail()
					+ " Time:" + activity.getTime();
			list.add(string);
		});
		return ResponseEntity.ok().body(list);
	}

}
