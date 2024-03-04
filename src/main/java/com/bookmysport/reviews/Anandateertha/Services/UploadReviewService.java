package com.bookmysport.reviews.Anandateertha.Services;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookmysport.reviews.Middlewares.GetNumberOfSlotsByUser;
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

    @Autowired
    private GetNumberOfSlotsByUser getNumberOfSlotsByUser;

    public ResponseEntity<ResponseMessage> uploadReviewService(String token, String role, ReviewDB review) {
        try {

            Map<String, Object> responseFromMW = getUserDetailsMW.getUserDetails(token, role).getBody();

            if (responseFromMW.size() != 0) {

                int numberOfBookingsByUser = getNumberOfSlotsByUser.getNumberOfSlotsOfAnUser(token, role).getBody().getNumberOfSlots();

                if (numberOfBookingsByUser != 0) {
                    String userName = responseFromMW.get("userName").toString();

                    ReviewDB reviewDB = new ReviewDB();

                    reviewDB.setPlaygroundId(review.getPlaygroundId());
                    reviewDB.setUserName(userName);
                    reviewDB.setReview(review.getReview());

                    LocalDate date = LocalDate.now();
                    reviewDB.setDate(date.toString());

                    reviewDB.setUserId(UUID.fromString(responseFromMW.get("id").toString()));

                    reviewRepo.save(reviewDB);

                    responseMessage.setSuccess(true);
                    responseMessage.setMessage("Review added");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                } else {
                    responseMessage.setSuccess(false);
                    responseMessage.setMessage("Not elgible for posting the review");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
                }

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
