package com.example.robinhood.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCommentRequestModel {

    @NotNull(message = "comment_id is mandatory")
    @JsonProperty(value = "comment_id")
    private Long commentId;

    @NotBlank(message = "description is mandatory")
    private String description;



}
