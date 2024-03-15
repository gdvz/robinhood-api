package com.example.robinhood.controller;

import com.example.robinhood.entity.Topic;
import com.example.robinhood.model.request.AddCommentRequestModel;
import com.example.robinhood.model.request.DeleteCommentRequestModel;
import com.example.robinhood.model.request.EditCommentRequestModel;
import com.example.robinhood.model.response.GetCommentResponseModel;
import com.example.robinhood.service.CommentService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @GetMapping("/getData")
    public ResponseEntity<List<GetCommentResponseModel>> getCommentByTopicId(@RequestParam Long topicId) {

        return ResponseEntity.ok(commentService.getCommentByTopicId(topicId));
    }

    @RateLimiter(name = "oneTime" , fallbackMethod = "limitHandler")
    @PostMapping("/add")
    public ResponseEntity<Topic> addComment(
        @Valid @RequestBody AddCommentRequestModel request) {

        boolean respData = commentService.add(request);

        if(!respData) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();

    }

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @PostMapping("/edit")
    public ResponseEntity<Topic> editComment(
        @Valid @RequestBody EditCommentRequestModel request) {

        boolean respData = commentService.edit(request);

        if(!respData) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();

    }

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @PostMapping("/delete")
    public ResponseEntity<Topic> deleteComment(
        @Valid @RequestBody DeleteCommentRequestModel request) {

        boolean respData = commentService.delete(request);

        if(!respData) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<String> limitHandler(RequestNotPermitted ex) {
        return new ResponseEntity<>("Too many Request", HttpStatus.TOO_MANY_REQUESTS);
    }

}
