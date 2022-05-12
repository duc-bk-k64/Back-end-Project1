package javaGuides.duc.Service;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import javaGuides.duc.DTO.FoodDTO;
import javaGuides.duc.Entity.Food;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Exception.ResourseNotFoundException;
import javaGuides.duc.Repository.FoodRepository;

@Service
public class FoodService {
	private FoodRepository foodRepository;

	public FoodService(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

	public void save(Food food) {
		foodRepository.save(food);
	}

	public String createFood(FoodDTO foodDTO) {
		try {
			Food food = foodRepository.findByCode(foodDTO.getCode());
			if(food!=null) return "Food "+foodDTO.getCode()+" already exist";
			food=new Food();
			food.setCalo(foodDTO.getCalo());
			food.setElement(foodDTO.getElement());
			food.setName(foodDTO.getName());
			food.setTime_create(Instant.now());
			food.setCode(foodDTO.getCode());
			foodRepository.save(food);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	public String updateFood(FoodDTO foodDTO) {
		try {
			Food food = foodRepository.findByCode(foodDTO.getCode());
			if (food == null)
				return "Food not found in system";
			food.setCalo(foodDTO.getCalo());
			food.setElement(foodDTO.getElement());
			food.setName(foodDTO.getName());
			food.setTime_update(Instant.now());
			foodRepository.save(food);
			return "Successfully";

		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

	public List<Food> getAll() {
		List<Food> list = foodRepository.findAll();
		return list;

	}

	public Food getFood(String code) {
		try {
			Food food = foodRepository.findByCode(code);
			return food;
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

}
