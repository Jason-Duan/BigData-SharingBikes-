package com.jason.service;

public interface UserService {

    boolean sendMsg(String nationCode, String phoneNum);

    boolean verify(String phoneNum, String verifyCode);

    void register(String phoneNum);
}
