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
    public List<String> A(@RequestBody MyRequest request) {
        return javaService.A(request);
    }

    @PostMapping("/B")
    public List<String> B(@RequestBody MyRequest request) {
        return javaService.B(request);
    }

    @GetMapping("/C")
    public List<String> C() throws InterruptedException {
        return javaService.C();
    }

    @GetMapping("/D")
    public List<String> D() throws InterruptedException {
        return javaService.D();
    }

}
