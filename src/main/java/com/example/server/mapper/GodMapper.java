package com.example.server.mapper;

import com.example.server.model.God;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GodMapper {
    List<God> allGod();
    List<God> getGodChetperAndSection(Map<String,String> map);
    List<God> getTenGodData(Map map);
    int getAllCount();
    List<God> getId(Long godId);
}
