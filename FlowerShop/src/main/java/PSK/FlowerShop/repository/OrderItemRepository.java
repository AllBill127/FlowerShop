package PSK.FlowerShop.repository;

import PSK.FlowerShop.entities.Admin;
import PSK.FlowerShop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}