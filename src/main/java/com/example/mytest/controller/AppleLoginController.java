package com.example.mytest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppleLoginController {

    @GetMapping("/apple")
    public String ApplePage() {
        System.out.println("와쓰");
        return "appleTest";
    }

    @PostMapping("/appleLoginTest")
    public  String  AppleLoginTest() {
        System.out.println("와쓰zzz");
        return "yotubeJsTest";
    }



}
