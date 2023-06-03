package com.example.server.mapper;

import com.example.server.model.Toeic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ToeicMapper {
    int toeicCount();
    int toeicCountData(String english);
    List<Toeic> toeicWords(Long toeicId);
    int toeicFromSubmit(Toeic toeic);
    int toeicFromSubmitEx(Map map);
    int setData(Map map);
    List<Toeic> queryToeicWords (String english);

}
