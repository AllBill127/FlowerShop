package PSK.FlowerShop.request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Setter @Getter
public class OrderRequest {
    private UUID id;
    private List<OrderItemRequest> orderItems;
    private Date createdAt;
    private BigDecimal totalPrice;
    private String status;
    private String phoneNumber;
    private String customerName;
    private String paymentMethod;
    private String shippingAddress;
}
