package com.example.server.service;

import com.example.server.mapper.ToeicMapper;
import com.example.server.model.Toeic;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToeicServiceImpl implements ToeicService {

    @Resource
    private ToeicMapper toeicMapper;

    private Integer randomNum;
    @Override
    public List<Toeic> toeicWords() {
        int id = toeicMapper.toeicCount();
        randomNum = (int) (Math.random() * id) + 1;
        return toeicMapper.toeicWords(Long.valueOf(randomNum));
    }

    @Override
    public void toeicFromSubmit(Toeic toeic) {
        toeicMapper.toeicFromSubmit(toeic);
    }

    @Override
    public List<Toeic> toeicFromSubmitEx(String example, String explain) {
        Map map = new HashMap<>();
        map.put("toeicId",randomNum);
        map.put("example",example);
        map.put("explain",explain);
        toeicMapper.toeicFromSubmitEx(map);
        return toeicMapper.toeicWords(Long.valueOf(randomNum));
    }

    @Override
    public List<Toeic> setData(String toeicId, String dialogEnglish, String dialogChinese, String dialogExample, String dialogExplain) {
        Map map = new HashMap<>();
        map.put("toeicId",Long.valueOf(toeicId));
        map.put("english",dialogEnglish);
        map.put("chinese",dialogChinese);
        map.put("example",dialogExample);
        map.put("explain",dialogExplain);
        toeicMapper.setData(map);
        return toeicMapper.toeicWords(Long.valueOf(toeicId));
    }


}
