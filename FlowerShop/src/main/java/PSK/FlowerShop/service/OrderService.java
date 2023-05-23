package PSK.FlowerShop.service;

import PSK.FlowerShop.Validators.ValidatorException;
import PSK.FlowerShop.entities.Order;
import PSK.FlowerShop.entities.OrderItem;
import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.repository.OrderItemRepository;
import PSK.FlowerShop.repository.OrderRepository;
import PSK.FlowerShop.repository.ProductRepository;
import PSK.FlowerShop.request.OrderItemRequest;
import PSK.FlowerShop.request.OrderRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        return orderRepository.findOrdersByDateRange(startDate, endDate);
    }


    public Order getOrderById(UUID id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public Order createOrder(OrderRequest orderRequest) throws ValidatorException {
        Order order = new Order(orderRequest.getCreatedAt(), orderRequest.getTotalPrice(), orderRequest.getStatus(), orderRequest.getPhoneNumber(), orderRequest.getCustomerName(), orderRequest.getPaymentMethod(), orderRequest.getShippingAddress(), orderRequest.getEmail());

        List<UUID> productIds = orderRequest.getOrderItems().stream()
                .map(OrderItemRequest::getProduct)
                .collect(Collectors.toList());
        List<Product> productList = productRepository.findAllById(productIds);
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (Product product : productList) {
            OrderItemRequest orderItemRequest = orderRequest.getOrderItems().stream().filter(orderItem -> product.getId().equals(orderItem.getProduct())).findFirst().orElse(null);
            if (orderItemRequest == null)
                throw new RuntimeException("Not exist id in orderItemRequest " + product.getId());
            if (product.getQuantity() < orderItemRequest.getQuantity())
                throw new RuntimeException("OrderItemRequest quantity too big " + product.getId());
            product.setQuantity(product.getQuantity() - orderItemRequest.getQuantity());
            OrderItem orderItem = new OrderItem(order, product, orderItemRequest.getQuantity(), orderItemRequest.getPrice());
            orderItemList.add(orderItem);
            order.addOrderItem(orderItem);
        }
        productRepository.saveAll(productList);
        order = orderRepository.save(order);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrder(order);
        }
        orderItemList = orderItemRepository.saveAll(orderItemList);
        System.out.println(orderItemList);
        if (orderItemList.isEmpty()) throw new ValidatorException("No order items saved");
        return order;
    }

    public Order updateOrder(UUID id, Order order) {
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

    public void deleteOrder(UUID id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
    }

    public Order changeOrderStatus(UUID id, String status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            return orderRepository.save(order);
        } else {
            throw new EntityNotFoundException("Order with id " + id + " not found");
        }
    }

    public Optional<OrderItem> getOrderItem(UUID id) {
        return orderItemRepository.findById(id);
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
