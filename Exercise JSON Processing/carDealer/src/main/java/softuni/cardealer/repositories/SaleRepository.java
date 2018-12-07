package softuni.cardealer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.cardealer.models.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
