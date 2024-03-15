package com.example.robinhood.model.response;

public interface GetCommentResponseModel {

    Long getId();
    String getCommentBy();
    String getDescription();
    String getCreateDateTime();
    String getOwnFlag();
}
