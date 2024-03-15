package com.example.robinhood.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "topic")
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="createById")
    private User createById;

    @Transient
    private String createBy;

    @Column(name = "create_date_time")
    private Date createDateTime;

    @Column(name = "collect")
    private String collect;

    @Column(name = "update_date_time")
    private Date updateDateTime;

    @OneToOne(mappedBy = "topicId", cascade = CascadeType.ALL)
    private TopicDetail topicDetail = new TopicDetail();

    @JsonManagedReference
    @OneToMany(mappedBy = "topicId", targetEntity = TopicHistory.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TopicHistory> topicHistory = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "topicId", targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> topicComment = new HashSet<>();

    public String getCreateBy() {
        return createById.getFullName();
    }

}