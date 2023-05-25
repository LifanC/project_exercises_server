package com.example.server.mapper;

import com.example.server.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UbikeMapper {

    List<Ubike> selectUbike(String sarea, String srcUpdateTime);

    int delDate(String srcUpdateTime);

    int insertUbike(Ubike ubike);


}
