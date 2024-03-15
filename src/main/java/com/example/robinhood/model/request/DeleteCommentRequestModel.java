package com.example.robinhood.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCommentRequestModel {

    @NotNull(message = "comment_id is mandatory")
    @JsonProperty(value = "comment_id")
    private Long commentId;


}
