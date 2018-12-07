package softuni.productshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.productshop.models.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	boolean existsByName(String name);
	
	List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal min, BigDecimal max);
	
	List<Product> findAllByBuyerIsNotNull();
	
	@Query("SELECT p FROM Category AS c JOIN c.products p ")
	List<Product> findAllProductsByCategory();
}
