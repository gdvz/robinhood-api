package com.example.robinhood.service.impl;

import com.example.robinhood.entity.Comment;
import com.example.robinhood.entity.Topic;
import com.example.robinhood.entity.User;
import com.example.robinhood.model.request.AddCommentRequestModel;
import com.example.robinhood.model.request.DeleteCommentRequestModel;
import com.example.robinhood.model.request.EditCommentRequestModel;
import com.example.robinhood.model.response.GetCommentResponseModel;
import com.example.robinhood.repository.CommentRepository;
import com.example.robinhood.repository.TopicRepository;
import com.example.robinhood.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public boolean add(AddCommentRequestModel request) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(!authentication.isAuthenticated()) {
                return false;
            }

            Optional<Topic> topicDataOptional = topicRepository.findById(request.getTopicId());

            if(topicDataOptional.isPresent()) {

                Comment comment = setDataComment(new Comment(),request.getDescription(),setUserInformationData(authentication),topicDataOptional.get());

                commentRepository.saveAndFlush(comment);

            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean edit(EditCommentRequestModel request) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(!authentication.isAuthenticated()) {
                return false;
            }

            Optional<Comment> commentDataOptional = commentRepository.findByIdAndUserId(request.getCommentId(),setUserInformationData(authentication).getId());

            if(commentDataOptional.isPresent()) {

                Comment comment = setDataComment(commentDataOptional.get(),request.getDescription(),setUserInformationData(authentication),commentDataOptional.get().getTopicId());

                commentRepository.saveAndFlush(comment);

            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(DeleteCommentRequestModel request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(!authentication.isAuthenticated()) {
                return false;
            }

            Optional<Comment> commentDataOptional = commentRepository.findByIdAndUserId(request.getCommentId(),setUserInformationData(authentication).getId());

            if(commentDataOptional.isPresent()) {
                commentRepository.deleteByIdCustom(commentDataOptional.get().getId());
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public List<GetCommentResponseModel> getCommentByTopicId(Long topicId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.isAuthenticated()) {
            return new ArrayList<>();
        }

        User userId = (User) authentication.getPrincipal();

        return commentRepository.findCommentByTopicId(topicId,userId.getId());
    }

    public User setUserInformationData(Authentication data) {

        User userData = new User();
        BeanUtils.copyProperties(data.getPrincipal(), userData);

        return userData;
    }

    public Comment setDataComment(Comment comment, String description, User user, Topic topic) {

        comment.setUserCommentId(user);
        comment.setTopicId(topic);
        comment.setDescription(description);

        comment.setCreateDateTime(comment.getId() == null ? new Date() : comment.getCreateDateTime());
        comment.setUpdateDateTime(new Date());

        return comment;

    }
}
