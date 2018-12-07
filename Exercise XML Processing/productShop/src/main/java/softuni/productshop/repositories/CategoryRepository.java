package softuni.productshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.productshop.models.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	boolean existsByName(String name);
	
	List<Category> findAllByOrderByProductsDesc();
}
