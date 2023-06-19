package com.example.server.controller;

import com.example.server.model.MyRequest;
import com.example.server.service.JavaService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/JavaExercise")
public class JavaExerciseController {

    @Resource
    private JavaService javaService;

    @PostMapping("/A")
    public void A(@RequestBody MyRequest request) throws InterruptedException {
        javaService.A(request);
    }

    @GetMapping("/A_show")
    public List<String> A_show() throws InterruptedException {
        return javaService.A_show();
    }

    @PostMapping("/B")
    public void B(@RequestBody MyRequest request) throws InterruptedException {
        javaService.B(request);
    }

    @GetMapping("/B_show")
    public List<String> B_show() throws InterruptedException {
        return javaService.B_show();
    }


    @GetMapping("/C")
    public List<String> C() throws InterruptedException {
        return javaService.C();
    }

    @GetMapping("/D")
    public void D() throws InterruptedException {
        javaService.D();
    }

}
