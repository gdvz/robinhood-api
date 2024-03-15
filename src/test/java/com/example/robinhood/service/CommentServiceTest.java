package com.example.robinhood.service;

import com.example.robinhood.entity.Comment;
import com.example.robinhood.entity.Topic;
import com.example.robinhood.entity.User;
import com.example.robinhood.model.request.AddCommentRequestModel;
import com.example.robinhood.model.request.DeleteCommentRequestModel;
import com.example.robinhood.model.request.EditCommentRequestModel;
import com.example.robinhood.repository.CommentRepository;
import com.example.robinhood.repository.TopicRepository;
import com.example.robinhood.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void addComment_Success() {
        AddCommentRequestModel request = new AddCommentRequestModel();
        request.setTopicId(1L);
        request.setDescription("Test comment");

        User user = new User();
        user.setId(1L);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        when(topicRepository.findById(any())).thenReturn(Optional.of(new Topic()));
        when(commentRepository.saveAndFlush(any())).thenReturn(new Comment());

        assertTrue(commentService.add(request));
    }

    @Test
    public void addComment_Failure_Unauthenticated() {
        AddCommentRequestModel request = new AddCommentRequestModel();
        request.setTopicId(1L);
        request.setDescription("Test comment");

        SecurityContextHolder.clearContext();

        assertFalse(commentService.add(request));
    }

    @Test
    public void editComment_Success() {
        EditCommentRequestModel request = new EditCommentRequestModel();
        request.setCommentId(1L);
        request.setDescription("Updated comment");

        User user = new User();
        user.setId(1L);

        Comment comment = new Comment();
        comment.setUserCommentId(user);
        comment.setTopicId(new Topic());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        when(commentRepository.findByIdAndUserId(request.getCommentId(), user.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.saveAndFlush(any())).thenReturn(new Comment());

        assertTrue(commentService.edit(request));
    }

    @Test
    public void editComment_Failure_Unauthenticated() {
        EditCommentRequestModel request = new EditCommentRequestModel();
        request.setCommentId(1L);
        request.setDescription("Updated comment");

        SecurityContextHolder.clearContext();

        assertFalse(commentService.edit(request));
    }

    @Test
    public void deleteComment_Success() {
        DeleteCommentRequestModel request = new DeleteCommentRequestModel();
        request.setCommentId(1L);

        User user = new User();
        user.setId(1L);

        Comment comment = new Comment();
        comment.setUserCommentId(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        when(commentRepository.findByIdAndUserId(request.getCommentId(), user.getId())).thenReturn(Optional.of(comment));

        assertTrue(commentService.delete(request));
    }

    @Test
    public void deleteComment_Failure_Unauthenticated() {
        DeleteCommentRequestModel request = new DeleteCommentRequestModel();
        request.setCommentId(1L);

        SecurityContextHolder.clearContext();

        assertFalse(commentService.delete(request));
    }

    @Test
    public void deleteComment_Failure_CommentNotFound() {
        DeleteCommentRequestModel request = new DeleteCommentRequestModel();
        request.setCommentId(1L);

        User user = new User();
        user.setId(1L);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(user);
        when(commentRepository.findByIdAndUserId(request.getCommentId(), user.getId())).thenReturn(Optional.empty());

        assertFalse(commentService.delete(request));
    }
}
