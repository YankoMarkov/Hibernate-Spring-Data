package softuni.userssystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.userssystem.models.Town;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
}
