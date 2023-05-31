package PSK.FlowerShop.service;

import PSK.FlowerShop.entities.Product;
import PSK.FlowerShop.entities.Review;
import PSK.FlowerShop.repository.ReviewRepository;
import PSK.FlowerShop.request.CategoryRequest;
import PSK.FlowerShop.request.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

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

//    public void updateReview(Review review) throws Exception {
//        if(reviewRepository.existsById(review.getId()))
//            reviewRepository.save(review);
//        else
//            throw new Exception(String.format(
//                    "Review to update with ID:{0} does not exist",
//                    review.getId()
//            ));
//    }

    public ReviewRequest updateReview(UUID id, ReviewRequest reviewRequest) throws Exception {
        Optional<Review> currentReview = reviewRepository.findById(id);
        if(!currentReview.isPresent())
            throw new Exception(String.format(
                    "Review with id:{0} now found!",
                    id
            ));
        Review review = currentReview.get();
        modelMapper.map(reviewRequest, review);

        Review updatedReview = reviewRepository.save(review);
        return reviewRequest;
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
