package com.example.server.controller;

import com.example.server.model.Toeic;
import com.example.server.service.ToeicService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@CrossOrigin(origins = "*")
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

    @PutMapping("/setData/{toeicId}/{dialogEnglish}/{dialogChinese}/{dialogExample}/{dialogExplain}")
    public List<Toeic> setData(
            @PathVariable String toeicId,
            @PathVariable String dialogEnglish,
            @PathVariable String dialogChinese,
            @PathVariable String dialogExample,
            @PathVariable String dialogExplain
    ) {
        return toeicService.setData(toeicId,dialogEnglish,dialogChinese,dialogExample,dialogExplain);
    }

}
