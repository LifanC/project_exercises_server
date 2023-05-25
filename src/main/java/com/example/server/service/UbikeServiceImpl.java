package com.example.server.service;

import com.example.server.mapper.UbikeMapper;
import com.example.server.model.Ubike;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class UbikeServiceImpl implements UbikeService {

    @Resource
    private UbikeMapper ubikeMapper;

    @Override
    public void delUbike() {
        Date date = new Date();
        SimpleDateFormat strDate = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = strDate.format(date);
        ubikeMapper.delDate(toDay);
    }

    @Override
    public void saveUbike(Ubike ubike) {
        ubikeMapper.insertUbike(ubike);
    }

    @Override
    public List<Ubike> getOnlyList(String sarea) {
        Date date = new Date();
        SimpleDateFormat strDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DATE,-1);
        String toDay = strDate.format(date);
        String yesDay = strDate.format(rightNow.getTime());
        List<Ubike> dataA = ubikeMapper.selectUbike(sarea,toDay);
        if(dataA.size() == 0){
            return ubikeMapper.selectUbike(sarea,yesDay);
        }else{
            return ubikeMapper.selectUbike(sarea,toDay);
        }
    }
}
