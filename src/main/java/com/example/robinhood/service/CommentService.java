package com.example.robinhood.service;

import com.example.robinhood.model.request.AddCommentRequestModel;
import com.example.robinhood.model.request.DeleteCommentRequestModel;
import com.example.robinhood.model.request.EditCommentRequestModel;
import com.example.robinhood.model.response.GetCommentResponseModel;

import java.util.List;

public interface CommentService {

    boolean add(AddCommentRequestModel request);
    boolean edit(EditCommentRequestModel request);
    boolean delete(DeleteCommentRequestModel request);
    List<GetCommentResponseModel> getCommentByTopicId(Long topicId);

}
