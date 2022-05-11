package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javaGuides.duc.DTO.MenuDTO;
import javaGuides.duc.Entity.Menu;
import javaGuides.duc.Service.MenuService;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
	private MenuService menuService;

	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}

	@PostMapping("/create")
	public ResponseEntity<Map<String, String>> createMenu(@RequestBody MenuDTO menuDTO) {
		Map<String, String> map = new HashMap<>();
		String message = menuService.createMenu(menuDTO);
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Map<String, String>> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
		Map<String, String> map = new HashMap<>();
		String message = menuService.updateMenu(id, menuDTO);
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/add/{menuID}")
	public ResponseEntity<Map<String, String>> addFood(@PathVariable Long menuID, @RequestParam Long foodID) {
		Map<String, String> map = new HashMap<>();
		String message = menuService.addFood(foodID, menuID);
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/remove/{menuID}")
	public ResponseEntity<Map<String, String>> removeFood(@PathVariable Long menuID, @RequestParam Long foodID) {
		Map<String, String> map = new HashMap<>();
		String message = menuService.removeFood(foodID, menuID);
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Map<String, String>> getByID(@PathVariable Long id) {
		Map<String, String> map = new HashMap<>();
		Menu menu = menuService.getByID(id);
		map.put("ID", String.valueOf(menu.getId()));
		map.put("Name", menu.getName());
		map.put("Comment", menu.getComment());
		map.put("Date", menu.getDate().toString());
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Map<String, String>>> getALL() {
		List<Map<String, String>> list = new ArrayList<>();
		List<Menu> set = menuService.getALL();
		set.forEach(menu -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(menu.getId()));
			map.put("Name", menu.getName());
			map.put("Comment", menu.getComment());
			map.put("Date", menu.getDate().toString());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);
	}

}
