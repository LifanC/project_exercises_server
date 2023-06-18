package com.example.server.service;

import com.example.server.model.MyRequest;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class JavaServiceImpl implements JavaService {

    @Resource
    RedisTemplate<String, String> redisTemplate;


    public void setSerializer() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
    }

    static class ThreadA extends Thread {
        public void run() {
            try {
                Lock.obj.a();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class ThreadB extends Thread {
        public void run() {
            try {
                Lock.obj.b();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class ThreadC extends Thread {
        public void run() {
            Lock.obj.c();
        }
    }

    static class ThreadD extends Thread {
        public void run() {
            Lock.obj.d();
        }
    }

    static class Lock {
        public static Lock obj = new Lock();

        public synchronized void a() throws InterruptedException {
            wait();
        }

        public synchronized void b() throws InterruptedException {
            wait();
        }

        public synchronized void c() {
            notifyAll();
        }

        public synchronized void d() {
            notifyAll();
        }

    }
    public void storeMap(String key, Map<String, String> dataMap) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, dataMap);
    }

    @Override
    public List<String> A(MyRequest request) {
        setSerializer();
        if(!"".equals(request.getA())){
            redisTemplate.opsForList().rightPush("list", request.getA());
        }
        ThreadA a = new ThreadA();
        a.start(); // a執行緒先執行
        List<String> elements = redisTemplate.opsForList().range("list", 0, -1);
        List<String> filter = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            if(request.getA().equals(elements.get(i))){
                filter.add(elements.get(i));
            }
        }
        return filter;
    }

    @Override
    public List<String> B(MyRequest request) {
        setSerializer();
        if(!"".equals(request.getB())){
            redisTemplate.opsForList().rightPush("list", request.getB());
        }
        ThreadB b = new ThreadB();
        b.start(); // b執行緒再接著執行
        List<String> elements = redisTemplate.opsForList().range("list", 0, -1);
        List<String> filter = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            if(request.getB().equals(elements.get(i))){
                filter.add(elements.get(i));
            }
        }
        return filter;
    }

    @Override
    public List<String> C() throws InterruptedException {
        ThreadC c = new ThreadC();
        c.start(); // c執行緒再接著執行
        c.join(); // 等c執行緒結束
        List<String> elements = redisTemplate.opsForList().range("list", 0, -1);
        return elements;
    }

    @Override
    public List<String> D() throws InterruptedException {
        ThreadC d = new ThreadC();
        d.start(); // d執行緒再接著執行
        d.join(); // 等d執行緒結束
        redisTemplate.delete("list");
        List<String> elements = redisTemplate.opsForList().range("list", 0, -1);
        return elements;
    }


}
