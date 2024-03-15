package com.example.robinhood.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditDataTopicRequestModel {

    @NotNull(message = "id is mandatory")
    private Long id;

    @NotBlank(message = "subject is mandatory")
    private String subject;

    private String description;

    @NotBlank(message = "status is mandatory")
    private String status;

}
