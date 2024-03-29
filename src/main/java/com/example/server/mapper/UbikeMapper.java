package com.example.server.mapper;

import com.example.server.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UbikeMapper {
    int truncateTable();
    int insertUbike(Ubike ubike);
    List<Ubike> selectUbike(String sarea, String ar, String srcUpdateTime);
    List<Ubike> selectUbikes(String sarea, String srcUpdateTime);


}
