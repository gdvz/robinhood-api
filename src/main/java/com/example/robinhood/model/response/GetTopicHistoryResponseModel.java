package com.example.robinhood.model.response;

public interface GetTopicHistoryResponseModel {

    Long getId();
    String getSubject();
    String getDescription();
    String getStatus();
    String getCreateBy();
    String getCreateDateTime();

}
