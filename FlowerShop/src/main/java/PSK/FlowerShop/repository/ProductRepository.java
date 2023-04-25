package PSK.FlowerShop.repository;

import PSK.FlowerShop.entities.Category;
import PSK.FlowerShop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByCategory(Category category);
}
