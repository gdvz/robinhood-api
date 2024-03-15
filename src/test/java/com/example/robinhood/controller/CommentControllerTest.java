package com.example.robinhood.controller;

import com.example.robinhood.entity.Topic;
import com.example.robinhood.model.request.AddCommentRequestModel;
import com.example.robinhood.model.request.DeleteCommentRequestModel;
import com.example.robinhood.model.request.EditCommentRequestModel;
import com.example.robinhood.model.response.GetCommentResponseModel;
import com.example.robinhood.service.CommentService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @Test
    public void testGetCommentByTopicId() {

        Long topicId = 1L;
        List<GetCommentResponseModel> comments = new ArrayList<>();
        comments.add(mockGetCommentResponseModel());

        when(commentService.getCommentByTopicId(topicId)).thenReturn(comments);

        ResponseEntity<List<GetCommentResponseModel>> responseEntity = commentController.getCommentByTopicId(topicId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(comments, responseEntity.getBody());

        verify(commentService, times(1)).getCommentByTopicId(topicId);
    }

    @Test
    public void testAddComment() {

        AddCommentRequestModel request = new AddCommentRequestModel();
        request.setDescription("Test comment");

        when(commentService.add(request)).thenReturn(true);

        ResponseEntity<Topic> responseEntity = commentController.addComment(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(commentService, times(1)).add(request);
    }

    @Test
    public void testEditComment() {

        EditCommentRequestModel request = new EditCommentRequestModel();
        request.setCommentId(1L);
        request.setDescription("Updated comment");

        when(commentService.edit(request)).thenReturn(true);

        ResponseEntity<Topic> responseEntity = commentController.editComment(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(commentService, times(1)).edit(request);
    }

    @Test
    public void testDeleteComment() {
        DeleteCommentRequestModel request = new DeleteCommentRequestModel();
        request.setCommentId(1L);

        when(commentService.delete(request)).thenReturn(true);

        ResponseEntity<Topic> responseEntity = commentController.deleteComment(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(commentService, times(1)).delete(request);
    }

    @Test
    public void testLimitHandler() {
        RequestNotPermitted exception = mock(RequestNotPermitted.class);

        ResponseEntity<String> responseEntity = commentController.limitHandler(exception);

        assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseEntity.getStatusCode());
        assertEquals("Too many Request", responseEntity.getBody());
    }

    private GetCommentResponseModel mockGetCommentResponseModel(){
        GetCommentResponseModel data = new GetCommentResponseModel() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getCommentBy() {
                return "jakkarin";
            }

            @Override
            public String getDescription() {
                return "good job sir";
            }

            @Override
            public String getCreateDateTime() {
                return "2021-02-02";
            }

            @Override
            public String getOwnFlag() {
                return "Y";
            }
        };

        return data;
    }
}
