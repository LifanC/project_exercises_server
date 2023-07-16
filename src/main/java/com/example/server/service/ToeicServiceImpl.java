package com.example.server.service;

import com.example.server.mapper.ToeicMapper;
import com.example.server.model.Toeic;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToeicServiceImpl implements ToeicService {

    @Resource
    private ToeicMapper toeicMapper;

    private int randomNum;

    @Override
    public List<Toeic> toeicWords() {
        int id_count = toeicMapper.toeicCount();
        randomNum = (int) (Math.random() * id_count) + 1;
        return (id_count > 0) ? toeicMapper.toeicWords((long) (randomNum)) : null;
    }

    @Override
    public void toeicFromSubmit(Toeic toeic) {
        toeicMapper.toeicFromSubmit(toeic);
    }

    @Override
    public List<Toeic> toeicFromSubmitEx(String example, String explain) {
        Map<String, Object> map = new HashMap<>();
        map.put("toeicId", randomNum);
        map.put("example", example);
        map.put("explain", explain);
        toeicMapper.toeicFromSubmitEx(map);
        return toeicMapper.toeicWords((long) (randomNum));
    }

    @Override
    public List<Toeic> setData(Toeic toeic) {
        Map<String, Object> map = new HashMap<>();
        map.put("toeicId", toeic.getToeicId());
        map.put("english", toeic.getEnglish());
        map.put("chinese", toeic.getChinese());
        map.put("example", toeic.getExample());
        map.put("explain", toeic.getExplain());
        toeicMapper.setData(map);
        return toeicMapper.toeicWords(toeic.getToeicId());
    }

    @Override
    public List<Toeic> queryToeicWords(String english) {
        return (english.equals("")) ? toeicMapper.toeicWords((long) (randomNum)) : toeicMapper.queryToeicWords(english);
    }

    @Override
    public boolean tf(String english) {
        int count = toeicMapper.toeicCountData(english);
        return count != 0;
    }

    @Override
    public List<Toeic> all() {
        return toeicMapper.all();
    }


}
