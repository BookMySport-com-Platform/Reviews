package com.bookmysport.reviews.Anandateertha.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmysport.reviews.Anandateertha.Services.EditAndDeleteReview;
import com.bookmysport.reviews.Anandateertha.Services.FetchReviews;
import com.bookmysport.reviews.Anandateertha.Services.UploadReviewService;
import com.bookmysport.reviews.Models.ResponseMessage;
import com.bookmysport.reviews.Models.ReviewDB;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private UploadReviewService uploadReviewService;

    @Autowired
    private FetchReviews fetchReviews;

    @Autowired
    private EditAndDeleteReview editAndDeleteReview;

    @PostMapping("addreview")
    public ResponseEntity<ResponseMessage> postReview(@RequestHeader String token, @RequestHeader String role,
            @RequestBody ReviewDB review) {
        return uploadReviewService.uploadReviewService(token, role, review);
    }

    @GetMapping("getreviews")
    public ResponseEntity<Object> getReviews(@RequestHeader String playgroundId) {
        return fetchReviews.fetchReviewsService(playgroundId);
    }

    @PutMapping("editreview")
    public ResponseEntity<ResponseMessage> editReview(@RequestHeader String token, @RequestHeader String role,
            @RequestBody ReviewDB latestReviewDB) {
        return editAndDeleteReview.editReviewService(token, role, latestReviewDB);
    }

    @DeleteMapping("deletereview")
    public ResponseEntity<ResponseMessage> deleteReview(@RequestHeader String token, @RequestHeader String role,
            @RequestHeader String reviewId) {
        return editAndDeleteReview.deleteReviewService(token, role, reviewId);
    }

}
