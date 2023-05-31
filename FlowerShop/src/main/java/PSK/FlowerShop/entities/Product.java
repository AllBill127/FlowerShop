package PSK.FlowerShop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;
    private String category;
    @OneToMany
    private List<Review> reviews;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private int quantity;
    @JsonIgnore
    @Version
    private Integer version;
    @JsonIgnore
    @Column(name = "IS_DELETED")
    private boolean isDeleted = false;

    public Product() {
    }

    public Product(String name, String category, String image, double price, int quantity, String description) {
        this.name = name;
        this.category = category;
        this.image = image;
        this.price = BigDecimal.valueOf(price);
        this.quantity = quantity;
        this.description = description;
        reviews = new ArrayList<>();
    }


    public void setReview(Review review) {
        if (reviews == null) reviews = new ArrayList<Review>();
        reviews.add(review);
    }
}
