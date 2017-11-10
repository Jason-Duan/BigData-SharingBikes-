package com.jason.controller;

import com.jason.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/genCode")
    @ResponseBody
    public boolean genCode(String nationCode, String phoneNum) {
        boolean flag = userService.sendMsg(nationCode, phoneNum);
        return flag;
    }

    @PostMapping("/verify")
    @ResponseBody
    public boolean verify(String phoneNum, String verifyCode) {
        boolean flag = userService.verify(phoneNum, verifyCode);
        return flag;
    }

    @PostMapping("/reg")
    @ResponseBody
    public String reg(String phoneNum) {
        userService.register(phoneNum);
        return "success";
    }

    @PostMapping("/host")
    @ResponseBody
    public String host() {
        String host = "";
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}
