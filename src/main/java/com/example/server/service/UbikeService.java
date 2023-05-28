package com.example.server.service;

import com.example.server.model.*;

import java.util.List;

public interface UbikeService {
    void queryUbike ();
    void saveUbike (Ubike ubike);
    List<Ubike> getOnlyList (String sarea,String ar);
    List<Ubike> getOnlyLists (String sarea);
}
