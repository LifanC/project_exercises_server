package com.example.server.service;

import com.example.server.model.MyRequest;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
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

    @Override
    public List<String> A(MyRequest request) {
        setSerializer();
        if (!"".equals(request.getA())) {
            redisTemplate.opsForList().rightPush("listA", request.getA());
        }
        ThreadA a = new ThreadA();
        a.start(); // a執行緒先執行
        return redisTemplate.opsForList().range("listA", 0, -1);
    }

    @Override
    public List<String> B(MyRequest request) {
        setSerializer();
        if (!"".equals(request.getB())) {
            redisTemplate.opsForList().rightPush("listB", request.getB());
        }
        ThreadB b = new ThreadB();
        b.start(); // b執行緒再接著執行
        return redisTemplate.opsForList().range("listB", 0, -1);
    }

    @Override
    public List<String> C() throws InterruptedException {
        ThreadC c = new ThreadC();
        c.start(); // c執行緒再接著執行
        c.join(); // 等c執行緒結束
        List<String> AB = new ArrayList<>();
        AB.addAll(Objects.requireNonNull(redisTemplate.opsForList().range("listA", 0, -1)));
        AB.addAll(Objects.requireNonNull(redisTemplate.opsForList().range("listB", 0, -1)));
        for (String all_listAB : AB) {
            redisTemplate.opsForList().rightPush("all_listAB",all_listAB);
        }
        return redisTemplate.opsForList().range("all_listAB",0,-1);
    }

    @Override
    public void D() throws InterruptedException {
        ThreadC d = new ThreadC();
        d.start(); // d執行緒再接著執行
        d.join(); // 等d執行緒結束
        redisTemplate.delete("listA");
        redisTemplate.delete("listB");
        redisTemplate.delete("all_listAB");
    }


}
