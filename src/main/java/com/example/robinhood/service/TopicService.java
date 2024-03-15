package com.example.robinhood.service;

import com.example.robinhood.model.request.AddTopicRequestModel;
import com.example.robinhood.model.request.EditDataTopicRequestModel;
import com.example.robinhood.model.response.GetDataTopicResponseModel;
import com.example.robinhood.model.response.GetTopicHistoryResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {

    Page<GetDataTopicResponseModel> findAll(Pageable pageable);

    GetDataTopicResponseModel findById(Long id);

    List<GetTopicHistoryResponseModel> findTopicHistoryByTopicId(Long id);

    boolean addTopic(AddTopicRequestModel request);

    boolean editDataTopic(EditDataTopicRequestModel request);

    boolean collectTopic(Long id);
}
