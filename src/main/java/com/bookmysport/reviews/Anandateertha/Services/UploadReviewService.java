package com.bookmysport.reviews.Anandateertha.Services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.reviews.Middlewares.GetUserDetailsMW;
import com.bookmysport.reviews.Models.ResponseMessage;
import com.bookmysport.reviews.Models.ReviewDB;
import com.bookmysport.reviews.Repositories.ReviewRepo;

@Service
public class UploadReviewService {

    @Autowired
    private GetUserDetailsMW getUserDetailsMW;

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> uploadReviewService(String token, String role, ReviewDB review) {
        try {
            ResponseMessage responseFromMW = getUserDetailsMW.getUserDetails(token, role).getBody();

            if (responseFromMW.getSuccess()) {

                String userName = getUserDetailsMW.getUserDetails(token, role).getBody().getMessage();

                ReviewDB reviewDB = new ReviewDB();

                reviewDB.setSpId(review.getSpId());
                reviewDB.setUserName(userName);
                reviewDB.setReview(review.getReview());

                LocalDate date = LocalDate.now();
                reviewDB.setDate(date.toString());

                reviewRepo.save(reviewDB);

                responseMessage.setSuccess(true);
                responseMessage.setMessage("Review added");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("User don't exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage(
                    "Internal Server Error in UploadReviewService.java. Method: uploadReviewService. Reason: "
                            + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }
}
