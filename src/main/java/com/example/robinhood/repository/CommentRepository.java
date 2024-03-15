package com.example.robinhood.repository;

import com.example.robinhood.entity.Comment;
import com.example.robinhood.model.response.GetCommentResponseModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query(value = "SELECT * FROM comment c WHERE c.id = :id AND c.create_by_id = :userId" , nativeQuery = true)
    Optional<Comment> findByIdAndUserId(Long id, Long userId);

    @Query(value = "SELECT c.id as id, u.full_name  as commentBy , c.description as description , c.create_date_time as createDateTime,CASE WHEN c.create_by_id = :userId THEN 'Y' ELSE 'N' END as ownFlag FROM comment c INNER JOIN user u on c.create_by_id = u.id WHERE c.topic_id  = :topicId ORDER BY c.id DESC" , nativeQuery = true)
    List<GetCommentResponseModel> findCommentByTopicId(Long topicId,Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment c WHERE c.id = :id" , nativeQuery = true)
    void deleteByIdCustom(Long id);
}
