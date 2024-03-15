package com.example.robinhood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comment")
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "create_date_time")
    private Date createDateTime;


    @Column(name = "description")
    private String description;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "create_by_id", referencedColumnName = "id")
    private User userCommentId;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Topic topicId;

    @Transient
    private String commentBy;

    @Column(name = "update_date_time")
    private Date updateDateTime;

    public String getCommentBy() {
        return userCommentId.getFullName();
    }
}