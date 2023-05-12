package PSK.FlowerShop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    private BigDecimal totalPrice;
    private String status;
    private String phoneNumber;
    private String customerName;
    private String paymentMethod;
    private String shippingAddress;

    public Order(Date createdAt, BigDecimal totalPrice, String status, String phoneNumber, String customerName, String paymentMethod, String shippingAddress) {
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;
        orderItems = new ArrayList<OrderItem>();
    }

    public Order() {

    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
    }

}
