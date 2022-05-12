package javaGuides.duc.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	Menu findByCode(String code);
}
