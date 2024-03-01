package com.bookmysport.reviews.Anandateertha.Services;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.reviews.Middlewares.GetUserDetailsMW;
import com.bookmysport.reviews.Models.ResponseMessage;
import com.bookmysport.reviews.Models.ReviewDB;
import com.bookmysport.reviews.Repositories.ReviewRepo;

@Service
public class EditAndDeleteReview {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private GetUserDetailsMW getUserDetailsMW;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> editReviewService(String token, String role, ReviewDB latestReview) {

        Map<String, Object> responseFromMW = getUserDetailsMW.getUserDetails(token, role).getBody();

        if (responseFromMW != null) {

            Optional<ReviewDB> reviewFromDB = reviewRepo.findById(latestReview.getReviewId());
            if (reviewFromDB.isPresent()) {

                if (responseFromMW.get("id").toString().equals(reviewFromDB.get().getUserId().toString())) {
                    reviewFromDB.get().setReview(latestReview.getReview());

                    reviewRepo.save(reviewFromDB.get());

                    responseMessage.setSuccess(true);
                    responseMessage.setMessage("Review Updated");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                } else {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Not authorized to edit this review");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
                }
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Review with this id does not exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }

        } else {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("User does not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

    }

    public ResponseEntity<ResponseMessage> deleteReviewService(String token, String role, String reviewId) {

        Map<String, Object> responseFromMW = getUserDetailsMW.getUserDetails(token, role).getBody();
        
        if (responseFromMW != null) {
            Optional<ReviewDB> reviewFromDB = reviewRepo.findById(UUID.fromString(reviewId));

            if (reviewFromDB.isPresent()) {
                if (responseFromMW.get("id").toString().equals(reviewFromDB.get().getUserId().toString())) {
                    reviewRepo.deleteById(UUID.fromString(reviewId));
                    responseMessage.setSuccess(true);
                    responseMessage.setMessage("Review Deleted");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                } else {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Not authorized to edit this review");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
                }
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Review with this id does not exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }

        } else {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("User does not exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

    }
}
