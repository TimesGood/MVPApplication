package com.example.core.api;


/**
 * 封装API的错误码
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}