package com.micro.reviewservice.service;

import com.micro.reviewservice.error.Message;
import com.micro.reviewservice.model.BuyHistory;
import com.micro.reviewservice.model.Product;
import com.micro.reviewservice.model.ReviewScore;
import com.micro.reviewservice.model.User;
import com.micro.reviewservice.repos.ProductRepos;
import com.micro.reviewservice.repos.ReviewScoreRepos;
import com.micro.reviewservice.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private ReviewScoreRepos reviewScoreRepos;
    private ProductRepos productRepos;
    private UserRepos userRepos;

    @Autowired
    public ReviewService(ReviewScoreRepos reviewScoreRepos, ProductRepos productRepos, UserRepos userRepos) {
        this.reviewScoreRepos = reviewScoreRepos;
        this.productRepos = productRepos;
        this.userRepos = userRepos;
    }

    public Message updateScore(Integer id, Integer score, String review) {
        ReviewScore reviewScore = reviewScoreRepos.findById(id).orElseThrow(()->new IllegalStateException("There is no such review"));
        if(score == null && review != null){
            reviewScore.setReview(review);
            reviewScoreRepos.save(reviewScore);
        }
        else if(score != null && review == null){
            reviewScore.setScore(score);
            reviewScoreRepos.save(reviewScore);
        }
        else if(score != null){
            reviewScore.setReview(review);
            reviewScore.setScore(score);
            reviewScoreRepos.save(reviewScore);
        }
        else throw new IllegalStateException("No values for review/score");
        return new Message(true);
    }

    public Message setReview(int product_id, int score, String description) {
        Product product = productRepos.findById(product_id).orElseThrow(()-> new IllegalStateException("There is no such product"));
        User apiUser = userRepos.findByUserUuid(getUserID()).orElseThrow();
        Optional<BuyHistory> buyHistoryOptional = apiUser.getBuyHistories().stream().filter(x->x.getProduct().equals(product)).findFirst();
        if (buyHistoryOptional.isEmpty()){
            throw new IllegalStateException("You can't review/score this product cos you didn't buy it");
        }
        else{
            ReviewScore reviewScore = new ReviewScore();
            reviewScore.setReview(description);
            reviewScore.setScore(score);
            reviewScore.setProduct(product);
            reviewScore.setUser(apiUser);
            reviewScoreRepos.save(reviewScore);
            return new Message(true);
        }
    }
    private String getUserID(){
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getTokenAttributes().get("sub");
    }
}
