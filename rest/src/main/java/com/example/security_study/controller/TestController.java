package com.example.security_study.controller;

import com.example.security_study.lib.Response;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/notice")
    public Response<String> testAdmin(){
        System.out.println("타나");
        return Response.builder(200 , " 공지사항 기능 입니다.").build();
    }

    @GetMapping("/board")
    public Response<String> testManager(){
        return Response.builder(200 , " Board 기능 입니다.").build();
    }

    @GetMapping("/popup")
    public Response<String> testUser(){
        return Response.builder(200 , " popup 기능 입니다.").build();
    }
}
