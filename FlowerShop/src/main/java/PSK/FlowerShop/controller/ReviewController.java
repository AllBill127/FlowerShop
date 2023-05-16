package PSK.FlowerShop.controller;

import PSK.FlowerShop.entities.Review;
import PSK.FlowerShop.request.ReviewRequest;
import PSK.FlowerShop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest) {
        Review review = new Review();
        review.setDescription(reviewRequest.getDescription());
        review.setReviewerName(reviewRequest.getReviewerName());
        reviewService.createReview(review);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) throws Exception {
        Optional<Review> review = reviewService.getReviewById(id);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/review/update/{id}")
    public ResponseEntity<ReviewRequest> updateCategory(@RequestBody ReviewRequest reviewRequest, @PathVariable UUID id) {
        try {
            ReviewRequest request = reviewService.updateReview(id, reviewRequest);
            return ResponseEntity.ok(request);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
