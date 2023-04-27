package PSK.FlowerShop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    private BigDecimal totalPrice;
    private String status;
    private String phoneNumber;
    private String customerName;
}
