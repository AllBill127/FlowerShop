package PSK.FlowerShop.controller;

import PSK.FlowerShop.entities.OrderItem;
import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.entities.Review;
import PSK.FlowerShop.request.ReviewRequest;
import PSK.FlowerShop.service.OrderService;
import PSK.FlowerShop.service.ProductService;
import PSK.FlowerShop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:3000")
@RequestScope

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest) throws Exception {
        try {
            Review review = new Review(reviewRequest.getReviewer(), reviewRequest.getComment(), reviewRequest.getRate());
            Optional<OrderItem> orderItem = orderService.getOrderItem(UUID.fromString(reviewRequest.getOrderItemID()));
            Optional<Product> product = productService.getProductById(UUID.fromString(reviewRequest.getProductID()));
            if (product.isEmpty() || orderItem.isEmpty()) return ResponseEntity.badRequest().build();
            review.setProduct(product.get());
            review.setOrderItem(orderItem.get());
            reviewService.createReview(review);
            OrderItem orderItem_ = orderItem.get();
            Product product_ = product.get();
            orderItem_.setReview(review);
            product_.setReview(review);
            if (productService.updateProduct(product_) == null || orderService.updateOrderItem(orderItem_) == null) {
                return ResponseEntity.badRequest().build();
            }
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch (java.lang.NullPointerException ex) {
            System.err.println(ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) throws Exception {
        Optional<Review> review = reviewService.getReviewById(id);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewRequest> updateCategory(@RequestBody ReviewRequest reviewRequest, @PathVariable UUID id) {
        try {
            ReviewRequest request = reviewService.updateReview(id, reviewRequest);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
