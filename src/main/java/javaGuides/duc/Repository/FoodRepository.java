package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.Food;
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

}
