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

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String,String>> updateFood(@PathVariable Long id, @RequestBody FoodDTO foodDTO) {
		String message = foodService.updateFood(id, foodDTO);
		Map<String,String> map=new HashMap<>();
		map.put("Message", message);
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/get/{id}")
//	@ResponseBody
	public ResponseEntity<Map<String, String>> getByID(@PathVariable Long id) {
//		String message = foodService.getById(id);
//		return new ResponseEntity<String>(message, HttpStatus.OK);
		Food food = foodService.getFood(id);
		Map<String, String> map = new HashMap<>();
		map.put("ID", String.valueOf(food.getId()));
		map.put("Name", food.getName());
		map.put("Element", food.getElement());
		map.put("Calo:", String.valueOf(food.getCalo()));
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
			map.put("Calo:", String.valueOf(food.getCalo()));
			list.add(map);
		});
		return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
	}

}
