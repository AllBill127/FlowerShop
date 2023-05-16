package PSK.FlowerShop.request;

import PSK.FlowerShop.entities.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Product product;
    private String reviewerName;
    private String description;
}
