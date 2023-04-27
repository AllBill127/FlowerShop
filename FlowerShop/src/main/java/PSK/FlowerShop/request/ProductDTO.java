package PSK.FlowerShop.request;

import PSK.FlowerShop.entities.Category;
import PSK.FlowerShop.entities.Review;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    private Category category;
    private List<Review> reviews;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    @Getter @Setter
    private String errorMessage;
}
