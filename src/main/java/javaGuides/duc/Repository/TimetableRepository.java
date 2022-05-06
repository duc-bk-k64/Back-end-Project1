package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.TimeTable;

@Repository
public interface TimetableRepository extends JpaRepository<TimeTable, Long> {

}
