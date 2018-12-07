package softuni.cardealer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.cardealer.models.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	
	boolean existsByMakeAndModelAndTravelledDistance(String make, String model, Long distance);
	
	List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
