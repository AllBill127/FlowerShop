package PSK.FlowerShop.request;

import PSK.FlowerShop.entities.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private double rate;
    private String comment;
    private String productID;
    private String orderItemID;
    private String reviewer;
}
