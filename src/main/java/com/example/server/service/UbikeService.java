package com.example.server.service;

import com.example.server.model.*;

import java.util.List;
import java.util.Map;

public interface UbikeService {
    void delUbike ();
    void saveUbike (Ubike ubike);
    List<Ubike> getOnlyList (String sarea);
}
