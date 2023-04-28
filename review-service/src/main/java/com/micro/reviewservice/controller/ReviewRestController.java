package com.micro.reviewservice.controller;

import com.micro.reviewservice.error.Message;
import com.micro.reviewservice.service.ReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ReviewRestController {
    private ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PutMapping(value = "/update")
    public Message setReviewScore(@RequestParam Integer id, @RequestParam(required = false) Integer score, @RequestParam(required = false) String review){
        return reviewService.updateScore(id,score,review);
    }
    @PostMapping("/set")
    public Message setReview(@RequestParam int product_id,@RequestParam int score,@RequestParam String description) throws IOException {
        return reviewService.setReview(product_id,score,description);
    }
}
