package com.example.server.controller;

import com.example.server.model.*;
import com.example.server.service.RateService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class RateController {

    @Resource
    private RateService rateService;

    @PostMapping("/getRate")
    public List<String> getRate(@RequestBody Rate rate) {
        return rateService.saveRate(rate);
    }

}
