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
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("chatper", chatper);
        map.put("skip", skip);
        return godMapper.getTenGodData(map);
    }

    @Override
    public List<God> tableArr() {
        int count = godMapper.getAllCount();
        int arrA = (int) (Math.random() * count) + 1;
        int arrB = (int) (Math.random() * count) + 1;
        int arrC = (int) (Math.random() * count) + 1;
        int arrD = (int) (Math.random() * count) + 1;
        int arrE = (int) (Math.random() * count) + 1;
        int arrF = (int) (Math.random() * count) + 1;
        List<Integer> dataCount = new ArrayList<>();
        dataCount.add(arrA);
        dataCount.add(arrB);
        dataCount.add(arrC);
        dataCount.add(arrD);
        dataCount.add(arrE);
        dataCount.add(arrF);
        List<List<God>> data = new ArrayList<>();
        List<God> datav = new ArrayList<>();
//        1.
//        for (Integer integer : dataCount) {
//            data.add(godMapper.getId(Long.valueOf(integer)));
//        }
        dataCount.forEach(e -> data.add(godMapper.getId(Long.valueOf(e))));
//        1.
//        data.forEach(e->{
//            e.forEach(a->{
//                datav.add(a);
//            });
//        });
//        2.
//        data.forEach(e->{
//            datav.addAll(e);
//        });
//        3.
//        data.forEach(datav::addAll);
        data.forEach(datav::addAll);
        return datav;
    }


}
