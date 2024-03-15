package com.example.robinhood.controller;

import com.example.robinhood.entity.Topic;
import com.example.robinhood.model.request.AddTopicRequestModel;
import com.example.robinhood.model.request.EditDataTopicRequestModel;
import com.example.robinhood.model.response.GetDataTopicResponseModel;
import com.example.robinhood.model.response.GetTopicHistoryResponseModel;
import com.example.robinhood.service.TopicService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TopicControllerTest {

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    @Test
    public void testGetDataTopic() {

        int page = 0;
        int pageSize = 3;
        Page<GetDataTopicResponseModel> pageResponse = mock(Page.class);

        when(topicService.findAll(any())).thenReturn(pageResponse);

        ResponseEntity<Page<GetDataTopicResponseModel>> responseEntity = topicController.getDataTopic(page, pageSize);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pageResponse, responseEntity.getBody());

        verify(topicService, times(1)).findAll(any());
    }

    @Test
    public void testGetDataTopicById() {

        Long id = 1L;
        GetDataTopicResponseModel responseModel = mock(GetDataTopicResponseModel.class);

        when(topicService.findById(id)).thenReturn(responseModel);

        ResponseEntity<GetDataTopicResponseModel> responseEntity = topicController.getDataTopicById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseModel, responseEntity.getBody());

        verify(topicService, times(1)).findById(id);
    }

    @Test
    public void testGetTopicHistoryById() {

        Long id = 1L;
        List<GetTopicHistoryResponseModel> historyResponse = Collections.singletonList(mock(GetTopicHistoryResponseModel.class));

        when(topicService.findTopicHistoryByTopicId(id)).thenReturn(historyResponse);

        ResponseEntity<List<GetTopicHistoryResponseModel>> responseEntity = topicController.getTopicHistoryById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(historyResponse, responseEntity.getBody());

        verify(topicService, times(1)).findTopicHistoryByTopicId(id);
    }

    @Test
    public void testAddTopic() {
        AddTopicRequestModel requestModel = mock(AddTopicRequestModel.class);

        when(topicService.addTopic(requestModel)).thenReturn(true);

        ResponseEntity<Topic> responseEntity = topicController.addTopic(requestModel);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(topicService, times(1)).addTopic(requestModel);
    }

    @Test
    public void testEditDataTopicById() {

        EditDataTopicRequestModel requestModel = mock(EditDataTopicRequestModel.class);

        when(topicService.editDataTopic(requestModel)).thenReturn(true);

        ResponseEntity<Topic> responseEntity = topicController.editDataTopicById(requestModel);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(topicService, times(1)).editDataTopic(requestModel);
    }

    @Test
    public void testCollectTopic() {
        Long id = 1L;

        when(topicService.collectTopic(id)).thenReturn(true);

        ResponseEntity<GetDataTopicResponseModel> responseEntity = topicController.collectTopic(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(topicService, times(1)).collectTopic(id);
    }

    @Test
    public void testLimitHandler() {
        RequestNotPermitted exception = mock(RequestNotPermitted.class);

        ResponseEntity<String> responseEntity = topicController.limitHandler(exception);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseEntity.getStatusCode());
        assertEquals("Too many Request", responseEntity.getBody());
    }
}
