package PSK.FlowerShop.repository;

import PSK.FlowerShop.entities.Category;
import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByProduct(Product product);
}
