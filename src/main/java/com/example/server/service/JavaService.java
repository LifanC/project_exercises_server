package com.example.server.service;

import com.example.server.model.MyRequest;

import java.util.List;
import java.util.Map;

public interface JavaService {
    List<String> A(MyRequest request);
    List<String> B(MyRequest request);
    List<String> C() throws InterruptedException;
    List<String> D() throws InterruptedException;
}
