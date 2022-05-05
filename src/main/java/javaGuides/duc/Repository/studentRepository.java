package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.Student;

@Repository
public interface studentRepository extends JpaRepository<Student, Long> {
	Student findByStudentCode(String studentCode);

}
