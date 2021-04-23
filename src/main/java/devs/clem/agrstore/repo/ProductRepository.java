package devs.clem.agrstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devs.clem.agrstore.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
