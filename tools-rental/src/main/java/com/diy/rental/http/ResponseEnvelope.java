package com.diy.rental.http;

import lombok.Data;

@Data
public class ResponseEnvelope<T> {
    private String status;
    private T data;

    public ResponseEnvelope(String status, T data) {
        this.status = status;
        this.data = data;
    }
    public ResponseEnvelope(String status) {
        this.status = status;
    }

}

