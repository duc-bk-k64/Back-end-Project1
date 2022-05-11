package javaGuides.duc.Service;
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
			Food food = new Food();
			food.setCalo(foodDTO.getCalo());
			food.setElement(foodDTO.getElement());
			food.setName(foodDTO.getName());
			foodRepository.save(food);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	public String updateFood(Long id, FoodDTO foodDTO) {
		try {
			Food food = foodRepository.getById(id);
			if (food == null)
				return "Food not found in system";
			food.setCalo(foodDTO.getCalo());
			food.setElement(foodDTO.getElement());
			food.setName(foodDTO.getName());
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

	public Food getFood(Long id) {
		try {
			Food food = foodRepository.getById(id);
			return food;
		} catch (Exception e) {
			throw new ResourseNotFoundException(e.getMessage());
		}
	}

}
