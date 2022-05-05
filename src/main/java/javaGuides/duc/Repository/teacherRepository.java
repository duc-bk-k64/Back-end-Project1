package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.teacher;

@Repository
public interface teacherRepository extends JpaRepository<teacher, Long> {
	teacher findByTeacherCode(String teacherCode);
}
