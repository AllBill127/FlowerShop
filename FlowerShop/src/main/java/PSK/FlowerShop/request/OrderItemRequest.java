package PSK.FlowerShop.request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter @Setter
public class OrderItemRequest {
    private UUID product;
    private Integer quantity;
    private BigDecimal price;
}
