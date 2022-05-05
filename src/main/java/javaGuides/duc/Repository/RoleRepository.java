package javaGuides.duc.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
