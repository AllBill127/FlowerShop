package PSK.FlowerShop.request;

import PSK.FlowerShop.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryRequest {
    private String name;
    private List<Product> products;
}
