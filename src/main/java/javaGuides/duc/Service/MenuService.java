package javaGuides.duc.Service;

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
			Menu menu = new Menu();
			menu.setComment(menuDTO.getComment());
			menu.setDate(menuDTO.getDate());
			menu.setName(menuDTO.getName());
			menuRepository.save(menu);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public String updateMenu(Long id, MenuDTO menuDTO) {
		try {
			Menu menu = menuRepository.getById(id);
			if (menu == null)
				return "Menu not found in system";
			menu.setComment(menuDTO.getComment());
			menu.setDate(menuDTO.getDate());
			menu.setName(menuDTO.getName());
			menuRepository.save(menu);
			return "Successfully";
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Menu getByID(Long id) {
		return menuRepository.getById(id);
	}

	public List<Menu> getALL() {
		return menuRepository.findAll();
	}

	public String addFood(Long foodID, Long menuID) {
		Food food = foodRepository.getById(foodID);
		Menu menu = menuRepository.getById(menuID);
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

	public String removeFood(Long foodID, Long menuID) {
		Food food = foodRepository.getById(foodID);
		Menu menu = menuRepository.getById(menuID);
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
