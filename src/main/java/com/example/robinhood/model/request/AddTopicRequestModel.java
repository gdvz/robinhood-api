package com.example.robinhood.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTopicRequestModel {

    @NotBlank(message = "subject is mandatory")
    private String subject;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NotBlank(message = "status is mandatory")
    private String status;

}
