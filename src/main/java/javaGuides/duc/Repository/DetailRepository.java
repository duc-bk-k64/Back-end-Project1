package javaGuides.duc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaGuides.duc.Entity.Detail;

@Repository
public interface DetailRepository extends JpaRepository<Detail,Long> {
  Detail findByCode(String code);
}
