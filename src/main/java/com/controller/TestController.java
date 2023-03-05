package com.controller;

import com.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("/oddInfo")
    public String oddInfo() {
        return testService.testOdd();
    }

}
