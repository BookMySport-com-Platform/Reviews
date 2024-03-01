package com.bookmysport.reviews.Anandateertha.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmysport.reviews.Anandateertha.Services.UploadReviewService;
import com.bookmysport.reviews.Models.ResponseMessage;
import com.bookmysport.reviews.Models.ReviewDB;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private UploadReviewService uploadReviewService;

    @PostMapping("addreview")
    public ResponseEntity<ResponseMessage> postReview(@RequestHeader String token,@RequestHeader String role,@RequestBody ReviewDB review)
    {
        return uploadReviewService.uploadReviewService(token, role, review);
    }

}
