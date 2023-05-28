package com.example.server.controller;

import com.example.server.model.Ubike;
import com.example.server.service.UbikeService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Transactional
@CrossOrigin(origins = "*")
public class UbikeController {

    @Resource
    private UbikeService ubikeService;

    @GetMapping("/queryUbike")
    public void delUbike(){
        ubikeService.queryUbike();
    }

    @PostMapping("/getUbike")
    public void getUbike(@RequestBody Ubike ubike) {
        ubikeService.saveUbike(ubike);
    }

    @GetMapping("/getOnlyList")
    public List<Ubike> getOnlyList(@Param("sarea") String sarea,@Param("ar") String ar){
        return ubikeService.getOnlyList(sarea,ar);

    }

    @GetMapping("/getOnlyLists")
    public List<Ubike> getOnlyLists(@Param("sarea") String sarea){
        return ubikeService.getOnlyLists(sarea);

    }

}
