package PSK.FlowerShop.controller;

import PSK.FlowerShop.Validators.Validator;
import PSK.FlowerShop.Validators.ValidatorException;
import PSK.FlowerShop.entities.Order;
import PSK.FlowerShop.request.OrderRequest;
import PSK.FlowerShop.service.EmailService;
import PSK.FlowerShop.usecase.EmailServiceImpl;
import PSK.FlowerShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")

public class OrderController {
    @Autowired
    private OrderService orderService;
    @Qualifier("emailServiceDecorator")
    @Autowired
    private EmailService emailService;
    @Autowired
    private Validator<OrderRequest> validator;


    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest order) {

        try {
            validator.validate(order);
            String orderID= orderService.createOrder(order).getId().toString();
            emailService.sendEmail(order.getEmail(),"Your order created", "Hello,\nWe would like to inform you that your order has been created.","http://localhost:3000/order/"+orderID);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderID);
        } catch (ValidatorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown error: "+e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable UUID id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }
    @PostMapping  ("/{id}")
    public Order changeOrderStatus(@PathVariable UUID id,  @RequestBody OrderRequest order) {
        return orderService.changeOrderStatus(order.getId(), order.getStatus());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }
}
