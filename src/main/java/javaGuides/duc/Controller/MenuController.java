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

	@PutMapping("/update")
	public ResponseEntity<Map<String, String>> updateMenu( @RequestBody MenuDTO menuDTO) {
		Map<String, String> map = new HashMap<>();
		String message = menuService.updateMenu(menuDTO);
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> addFood(@RequestParam String menuCode, @RequestParam String foodCode) {
		Map<String, String> map = new HashMap<>();
		String message = menuService.addFood(foodCode, menuCode);
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PostMapping("/remove")
	public ResponseEntity<Map<String, String>> removeFood(@RequestParam String menuCode, @RequestParam String foodCode) {
		Map<String, String> map = new HashMap<>();
		String message = menuService.removeFood(foodCode, menuCode);
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/get")
	public ResponseEntity<Map<String, String>> getByCode(@RequestParam String code) {
		Map<String, String> map = new HashMap<>();
		Menu menu = menuService.getByCode(code);
		if(menu==null) {
			map.put("Message","Menu not found in system");
			return ResponseEntity.badRequest().body(map);
		}
		map.put("ID", String.valueOf(menu.getId()));
		map.put("Name", menu.getName());
		map.put("Comment", menu.getComment());
		map.put("Date", menu.getDate().toString());
		map.put("Code", code);
		List<String>  list=new ArrayList<>();
		menu.getFoods().forEach(food->{
			list.add(food.getName());
		});
		map.put("Food",list.toString());
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
			map.put("Code",menu.getCode());
			list.add(map);
		});
		return ResponseEntity.ok().body(list);
	}

}
