package com.example.server.service;

import com.example.server.model.God;

import java.util.List;

public interface GodService {
    List<God> allgod();
    List<God> getGodChetperAndSection(String name, String chatper);
    List<God> getTenGodData(String name, String chatper, Long skip);
    List<God> tableArr();

}
