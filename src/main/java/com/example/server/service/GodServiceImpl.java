package com.example.server.service;

import com.example.server.mapper.GodMapper;
import com.example.server.model.God;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GodServiceImpl implements GodService {
    @Resource
    private GodMapper godMapper;

    @Override
    public List<God> allgod() {
        return godMapper.allGod();
    }

    @Override
    public List<God> getGodChetperAndSection(String name, String chatper) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("chatper", chatper);
        return godMapper.getGodChetperAndSection(map);
    }

    @Override
    public List<God> getTenGodData(String name, String chatper, Long skip) {
        Map map = new HashMap<>();
        map.put("name", name);
        map.put("chatper", chatper);
        map.put("skip", skip);
        return godMapper.getTenGodData(map);
    }

    @Override
    public List<God> tableArr() {
        int count = godMapper.getAllCount();
        Integer arrA = (int) (Math.random() * count) + 1;
        Integer arrB = (int) (Math.random() * count) + 1;
        Integer arrC = (int) (Math.random() * count) + 1;
        Integer arrD = (int) (Math.random() * count) + 1;
        Integer arrE = (int) (Math.random() * count) + 1;
        Integer arrF = (int) (Math.random() * count) + 1;
        List<Long> dataCount = new ArrayList<>();
        dataCount.add(Long.valueOf(arrA));
        dataCount.add(Long.valueOf(arrB));
        dataCount.add(Long.valueOf(arrC));
        dataCount.add(Long.valueOf(arrD));
        dataCount.add(Long.valueOf(arrE));
        dataCount.add(Long.valueOf(arrF));
        List<List<God>> data = new ArrayList<>();
        List<God> datav = new ArrayList<>();
        for (int i = 0; i < dataCount.size(); i++) {
            data.add(godMapper.getId(dataCount.get(i)));
        }
        data.forEach(e->{
            e.forEach(a->{
                datav.add(a);
            });
        });
        return datav;
    }


}
