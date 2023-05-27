package com.example.server.service;

import com.example.server.model.Toeic;

import java.util.List;

public interface ToeicService {
    List<Toeic> toeicWords();

    void toeicFromSubmit(Toeic toeic);

    List<Toeic> toeicFromSubmitEx(String example, String explain);

    List<Toeic> setData(String toeicId, String dialogEnglish, String dialogChinese, String dialogExample, String dialogExplain);

    List<Toeic> queryToeicWords(String english);

}
