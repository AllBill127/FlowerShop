package PSK.FlowerShop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonIgnore
    @ManyToOne
    private Product product;
    @JsonIgnore
    @OneToOne
    private OrderItem orderItem;

    private String reviewerName;
    private String comment;
    private double rate;

    public Review(String reviewerName, String comment, double rate) {
        this.reviewerName = reviewerName;
        this.comment = comment;
        this.rate = rate;
    }

    public Review() {

    }
}
