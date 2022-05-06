package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.Form;

@Repository
public interface FormRepository extends JpaRepository<Form,Long> {

}
