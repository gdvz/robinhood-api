package com.example.robinhood.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCommentRequestModel {

    @NotNull(message = "topic_id is mandatory")
    @JsonProperty(value = "topic_id")
    private Long topicId;

    @NotBlank(message = "description is mandatory")
    private String description;



}
