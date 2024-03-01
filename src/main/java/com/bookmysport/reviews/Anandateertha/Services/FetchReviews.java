package com.bookmysport.reviews.Anandateertha.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.reviews.Models.ResponseMessage;
import com.bookmysport.reviews.Models.ReviewDB;
import com.bookmysport.reviews.Repositories.ReviewRepo;

@Service
public class FetchReviews {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ResponseMessage responseMessage;
    
    public ResponseEntity<Object> fetchReviewsService(String spId) {
        try {
            List<ReviewDB> reviews = reviewRepo.findBySpId(UUID.fromString(spId));
            if (!reviews.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(reviews);
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("No reviews");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal server error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
}
