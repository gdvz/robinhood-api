package com.example.robinhood.service.impl;

import com.example.robinhood.entity.Topic;
import com.example.robinhood.entity.TopicHistory;
import com.example.robinhood.entity.User;
import com.example.robinhood.model.request.AddTopicRequestModel;
import com.example.robinhood.model.request.EditDataTopicRequestModel;
import com.example.robinhood.model.response.GetDataTopicResponseModel;
import com.example.robinhood.model.response.GetTopicHistoryResponseModel;
import com.example.robinhood.repository.TopicRepository;
import com.example.robinhood.service.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Page<GetDataTopicResponseModel> findAll(Pageable pageable) {

        return topicRepository.findALlWithNotCollectTopic(pageable);
    }

    @Override
    public GetDataTopicResponseModel findById(Long id) {

        return topicRepository.getDetailByTopicId(id);
    }

    @Override
    public List<GetTopicHistoryResponseModel> findTopicHistoryByTopicId(Long id) {
        return topicRepository.getTopicHistoryByTopicId(id);
    }

    @Override
    public boolean addTopic(AddTopicRequestModel request) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = setUserInformationData(authentication);

            Topic topic = new Topic();
            topic.setSubject(request.getSubject());
            topic.setCollect("N");
            topic.setCreateById(user);
            topic.setCreateDateTime(new Date());
            topic.setUpdateDateTime(new Date());

            topic.getTopicDetail().setTopicId(topic);
            topic.getTopicDetail().setDescription(request.getDescription());
            topic.getTopicDetail().setStatus(request.getStatus());

            topicRepository.saveAndFlush(topic);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean editDataTopic(EditDataTopicRequestModel request) {

        try {

            Optional<Topic> topicData = topicRepository.findById(request.getId());

            if(topicData.isPresent()) {

                TopicHistory topicHistory = setTopicHistory(new TopicHistory(),topicData.get());

                topicRepository.saveAndFlush(setTopicEditData(topicData.get(),request,topicHistory));
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean collectTopic(Long id) {

        try {

            Optional<Topic> topicData = topicRepository.findById(id);

            if(topicData.isPresent()) {

                topicData.get().setCollect("Y");
                topicData.get().setUpdateDateTime(new Date());

                topicRepository.saveAndFlush(topicData.get());
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    private Topic setTopicEditData(Topic topic,EditDataTopicRequestModel request,TopicHistory topicHistory) {

        topic.setSubject(request.getSubject());
        topic.getTopicDetail().setDescription(request.getDescription());
        topic.getTopicDetail().setStatus(request.getStatus());

        topic.getTopicHistory().add(topicHistory);

        topic.setUpdateDateTime(new Date());

        return topic;

    }

    private TopicHistory setTopicHistory(TopicHistory topicHistory,Topic topic) {

        topicHistory.setTopicSubject(topic.getSubject());
        topicHistory.setTopicDescription(topic.getTopicDetail().getDescription());
        topicHistory.setTopicStatus(topic.getTopicDetail().getStatus());
        topicHistory.setTopicId(topic);

        topicHistory.setCreateDateTime(new Date());

        return topicHistory;

    }

    public User setUserInformationData(Authentication data) {

        User userData = new User();
        BeanUtils.copyProperties(data.getPrincipal(), userData);

        return userData;
    }

}
