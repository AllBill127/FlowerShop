package PSK.FlowerShop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
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
    private int quantity;
}
