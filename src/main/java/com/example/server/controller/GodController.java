package com.example.server.controller;

import com.example.server.model.God;
import com.example.server.service.GodService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Transactional
@RequestMapping("/god")
public class GodController {

    @Resource
    private GodService godService;

    @GetMapping("/allgod")
    public List<God> allgod(){
        return godService.allgod();
    }

    @GetMapping("/getGodOnly")
    public List<God> getGodOnly(
            @Param("name") String name,
            @Param("chatper") String chatper
    ){
        return godService.getGodChetperAndSection(name,chatper);
    }

    @GetMapping("/getTenGodData")
    public List<God> getTenGodData(
            @Param("name") String name,
            @Param("chatper") String chatper,
            @Param("skip") Long skip
    ){
        return godService.getTenGodData(name,chatper,skip);
    }

    @GetMapping("/tableArr")
    public List<God> tableArr(){
        return godService.tableArr();
    }


}
