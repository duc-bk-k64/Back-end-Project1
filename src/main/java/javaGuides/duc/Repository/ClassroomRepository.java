package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.ClassRoom;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassRoom, Long> {
	ClassRoom findByName(String name);
}
