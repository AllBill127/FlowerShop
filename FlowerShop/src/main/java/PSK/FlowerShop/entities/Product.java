package PSK.FlowerShop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Category category;
    @OneToMany
    private List<Review> reviews;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}
