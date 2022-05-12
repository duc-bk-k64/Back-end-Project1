package javaGuides.duc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javaGuides.duc.DTO.FoodDTO;
import javaGuides.duc.Entity.Food;
import javaGuides.duc.Service.FoodService;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {
	private FoodService foodService;

	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String,String>> createFood(@RequestBody FoodDTO foodDTO) {
		String message = foodService.createFood(foodDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String,String>> updateFood( @RequestBody FoodDTO foodDTO) {
		String message = foodService.updateFood(foodDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/get")
	public ResponseEntity<Map<String, String>> getByID(@RequestParam String code) {
		Food food = foodService.getFood(code);
		Map<String, String> map = new HashMap<>();
		if(food==null) {
			map.put("Message","Food not found in system");
			return ResponseEntity.badRequest().body(map);
		}
		map.put("ID", String.valueOf(food.getId()));
		map.put("Name", food.getName());
		map.put("Element", food.getElement());
		map.put("Calo:", String.valueOf(food.getCalo()));
		map.put("Code",food.getCode());
		return ResponseEntity.ok().body(map);

	}

	@GetMapping("/all")
	public ResponseEntity<List<Map<String, String>>> getAll() {
		List<Map<String, String>> list = new ArrayList<>();
		foodService.getAll().forEach(food -> {
			Map<String, String> map = new HashMap<>();
			map.put("ID", String.valueOf(food.getId()));
			map.put("Name", food.getName());
			map.put("Element", food.getElement());
			map.put("Code",food.getCode());
			map.put("Calo:", String.valueOf(food.getCalo()));
			list.add(map);
		});
		return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
	}

}
