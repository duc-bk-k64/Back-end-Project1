package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long>{

}
