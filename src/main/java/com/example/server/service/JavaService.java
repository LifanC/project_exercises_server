package com.example.server.service;

import com.example.server.model.MyRequest;

import java.util.List;
import java.util.Map;

public interface JavaService {
    void A(MyRequest request) throws InterruptedException;
    List<String> A_show() throws InterruptedException;
    void B(MyRequest request) throws InterruptedException;
    List<String> B_show() throws InterruptedException;
    List<String> C() throws InterruptedException;
    void D() throws InterruptedException;
}
