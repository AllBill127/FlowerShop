package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.entities.Review;
import PSK.FlowerShop.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public void createReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByProduct(Product product) {
        return reviewRepository.findAllByProduct(product);
    }

    public Optional<Review> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    public void updateReview(Review review) throws Exception {
        if(reviewRepository.existsById(review.getId()))
            reviewRepository.save(review);
        else
            throw new Exception(String.format(
                    "Review to update with ID:{0} does not exist",
                    review.getId()
            ));
    }

    public void deleteReview(UUID id) throws Exception {
        if(reviewRepository.existsById(id))
            reviewRepository.deleteById(id);
        else
            throw new Exception(String.format(
                    "Review to delete with ID:{0} does not exist",
                    id
            ));
    }
}
