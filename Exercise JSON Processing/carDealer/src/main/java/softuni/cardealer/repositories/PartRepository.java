package softuni.cardealer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.cardealer.models.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
	
	boolean existsByName(String name);
}
