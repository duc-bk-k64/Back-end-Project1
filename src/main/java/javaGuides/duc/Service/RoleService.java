package javaGuides.duc.Service;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

import javaGuides.duc.Entity.Role;
import javaGuides.duc.Repository.RoleRepository;

@Service
public class RoleService {
	private RoleRepository repository;

	public RoleService(RoleRepository repository) {
		this.repository = repository;
	}

	public String Create(String role) {
		Role role2 = new Role();
		role2.setName(role);
		repository.save(role2);
		return "Successfully";

	}

}
