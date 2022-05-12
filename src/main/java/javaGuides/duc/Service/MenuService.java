package javaGuides.duc.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import javaGuides.duc.DTO.MenuDTO;
import javaGuides.duc.Entity.Food;
import javaGuides.duc.Entity.Menu;
import javaGuides.duc.Exception.BadRequestException;
import javaGuides.duc.Repository.FoodRepository;
import javaGuides.duc.Repository.MenuRepository;

@Service
public class MenuService {
	private MenuRepository menuRepository;
	private FoodRepository foodRepository;

	public MenuService(MenuRepository menuRepository, FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
		this.menuRepository = menuRepository;
	}

	public String createMenu(MenuDTO menuDTO) {
		try {
			Menu menu = menuRepository.findByCode(menuDTO.getCode());
			if(menu!=null) return "Menu "+menuDTO.getCode()+" already exist";
			menu=new Menu();
			menu.setComment(menuDTO.getComment());
			menu.setDate(menuDTO.getDate());
			menu.setName(menuDTO.getName());
			menu.setTime_create(Instant.now());
			menu.setCode(menuDTO.getCode());
			menuRepository.save(menu);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public String updateMenu( MenuDTO menuDTO) {
		try {
			Menu menu = menuRepository.findByCode(menuDTO.getCode());
			if (menu == null)
				return "Menu not found in system";
			menu.setComment(menuDTO.getComment());
			menu.setDate(menuDTO.getDate());
			menu.setName(menuDTO.getName());
			menu.setTime_update(Instant.now());
			menuRepository.save(menu);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Menu getByCode(String code) {
		return menuRepository.findByCode(code);
	}

	public List<Menu> getALL() {
		return menuRepository.findAll();
	}

	public String addFood(String foodCode,String menuCode) {
		Food food = foodRepository.findByCode(foodCode);
		Menu menu = menuRepository.findByCode(menuCode);
		if (food == null)
			return "Food not found in system";
		if (menu == null)
			return "Menu not found in system";
		Set<Food> set = menu.getFoods();
		set.add(food);
		menu.setFoods(set);
		menuRepository.save(menu);
		return "Successfully";
	}

	public String removeFood(String foodCode,String menuCode) {
		Food food = foodRepository.findByCode(foodCode);
		Menu menu = menuRepository.findByCode(menuCode);
		if (food == null)
			return "Food not found in system";
		if (menu == null)
			return "Menu not found in system";
		Set<Food> set = menu.getFoods();
		if (set.contains(food))
			set.remove(food);
		menu.setFoods(set);
		menuRepository.save(menu);
		return "Successfully";
	}
}
