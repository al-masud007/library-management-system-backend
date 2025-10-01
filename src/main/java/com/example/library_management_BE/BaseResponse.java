package com.example.library_management_BE;


import lombok.Data;

@Data
public class BaseResponse {
    private final boolean success;
    private final String message;
    private final int statusCode;


}