package com.example.robinhood.repository;

import com.example.robinhood.entity.Topic;
import com.example.robinhood.model.response.GetDataTopicResponseModel;
import com.example.robinhood.model.response.GetTopicHistoryResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {

    @Query(value = "SELECT t.id as id,t.subject as subject, td.description as description, td.status as status, u.full_name as createBy,u.email as email ,t.create_date_time  as createDate FROM topic t INNER JOIN topic_detail td on t.id=td.topic_id INNER JOIN user u on t.create_by_id=u.id WHERE t.collect = 'N'" ,
        countQuery = "SELECT COUNT(*) FROM topic t WHERE t.collect = 'N'" ,
        nativeQuery = true)
    Page<GetDataTopicResponseModel> findALlWithNotCollectTopic(Pageable pageable);

    @Query(value = "SELECT t.id as id,t.subject as subject, td.description as description, td.status as status, u.full_name as createBy, u.email as email, t.create_date_time as createDate FROM topic t INNER JOIN topic_detail td on t.id=td.topic_id INNER JOIN user u on t.create_by_id=u.id WHERE t.id = :topicId" , nativeQuery = true)
    GetDataTopicResponseModel getDetailByTopicId(Long topicId);

    @Query(value = "SELECT th.id as id,th.topic_subject as subject,th.topic_description as description, th.topic_status as status, u.full_name as createBy , th.create_date_time as createDateTime FROM topic t INNER JOIN topic_history th on t.id=th.topic_id INNER JOIN user u on t.create_by_id=u.id WHERE th.topic_id = :topicId ORDER BY th.id DESC" , nativeQuery = true)
    List<GetTopicHistoryResponseModel> getTopicHistoryByTopicId(Long topicId);

}
