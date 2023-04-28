package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Order;
import PSK.FlowerShop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
    }

    public Order createOrder(Order order) {
        order.setId(0);
        order.setCreatedAt((java.sql.Date) new Date(System.currentTimeMillis()));

        return orderRepository.save(order);
    }

    public Order updateOrder(int id, Order order) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order existingOrder = orderOptional.get();
            existingOrder.setOrderItems(order.getOrderItems());
            existingOrder.setTotalPrice(order.getTotalPrice());
            existingOrder.setStatus(order.getStatus());
            existingOrder.setPhoneNumber(order.getPhoneNumber());
            existingOrder.setCustomerName(order.getCustomerName());

            return orderRepository.save(existingOrder);
        } else {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
    }

    public void deleteOrder(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
    }
}
