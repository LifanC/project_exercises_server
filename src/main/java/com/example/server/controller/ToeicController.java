package com.example.server.controller;

import com.example.server.model.Toeic;
import com.example.server.service.ToeicService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@CrossOrigin(origins = "*")
@RequestMapping("/toeic")
public class ToeicController {

    @Resource
    private ToeicService toeicService;

    @GetMapping("/toeicWords")
    public List<Toeic> toeicWords() {
        return toeicService.toeicWords();
    }

    @PostMapping("/toeicFromSubmit")
    public void toeicFromSubmit(@RequestBody Toeic toeic) {
        toeicService.toeicFromSubmit(toeic);
    }

    @PutMapping("/toeicFromSubmitEx/{example}/{explain}")
    public List<Toeic> toeicFromSubmitEx(
            @PathVariable String example,
            @PathVariable String explain
    ) {
        return toeicService.toeicFromSubmitEx(example, explain);
    }

    @PutMapping("/setData")
    public List<Toeic> setData(@RequestBody Toeic toeic) {
        return toeicService.setData(toeic);
    }

    @GetMapping("/queryToeicWords")
    public List<Toeic> queryToeicWords(@Param("english") String english){
        return toeicService.queryToeicWords(english);
    }

    @GetMapping("/tf")
    public boolean tf(@Param("english") String english){
        return toeicService.tf(english);
    }

    @GetMapping("/all")
    public List<Toeic> all(){
        return toeicService.all();
    }

}
