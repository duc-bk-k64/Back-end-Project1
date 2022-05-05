package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	User findByUsername(String username);

	User findByTokenForgotPassword(String token);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
