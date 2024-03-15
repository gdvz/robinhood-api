package com.example.robinhood.controller;

import com.example.robinhood.entity.Topic;
import com.example.robinhood.model.request.AddTopicRequestModel;
import com.example.robinhood.model.request.EditDataTopicRequestModel;
import com.example.robinhood.model.response.GetDataTopicResponseModel;
import com.example.robinhood.model.response.GetTopicHistoryResponseModel;
import com.example.robinhood.service.TopicService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @GetMapping("/getData")
    public ResponseEntity<Page<GetDataTopicResponseModel>> getDataTopic(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "3") int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize,Sort.by("id").ascending());

        return ResponseEntity.ok(topicService.findAll(pageable));
    }

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @GetMapping("/getDetail/{id}")
    public ResponseEntity<GetDataTopicResponseModel> getDataTopicById(
        @PathVariable Long id) {

        GetDataTopicResponseModel respData = topicService.findById(id);

        if(respData == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(respData);

    }

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @GetMapping("/getHistory/{id}")
    public ResponseEntity<List<GetTopicHistoryResponseModel>> getTopicHistoryById(
        @PathVariable Long id) {

        return ResponseEntity.ok(topicService.findTopicHistoryByTopicId(id));
    }

    @RateLimiter(name = "oneTime" , fallbackMethod = "limitHandler")
    @PostMapping("/add")
    public ResponseEntity<Topic> addTopic(
        @Valid @RequestBody AddTopicRequestModel request) {

        boolean respData = topicService.addTopic(request);

        if(!respData) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();

    }

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @PostMapping("/editData")
    public ResponseEntity<Topic> editDataTopicById(
        @Valid @RequestBody EditDataTopicRequestModel request) {

        boolean respData = topicService.editDataTopic(request);

        if(!respData) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();

    }

    @RateLimiter(name = "backendA" , fallbackMethod = "limitHandler")
    @GetMapping("/collect")
    public ResponseEntity<GetDataTopicResponseModel> collectTopic(
        @RequestParam Long id) {

        boolean respData = topicService.collectTopic(id);

        if(!respData) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();

    }

    public ResponseEntity<String> limitHandler(RequestNotPermitted t) {
        return new ResponseEntity<>("Too many Request", HttpStatus.TOO_MANY_REQUESTS);
    }

}
