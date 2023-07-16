package com.example.server.service;

import com.example.server.model.MyRequest;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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

    static class ThreadA_show extends Thread {
        public void run() {
            Lock.obj.a_show();
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

    static class ThreadB_show extends Thread {
        public void run() {
            Lock.obj.b_show();
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

        public synchronized void a_show() {
            notifyAll();
        }

        public synchronized void b() throws InterruptedException {
            wait();
        }

        public synchronized void b_show() {
            notifyAll();
        }

        public synchronized void c() {
            notifyAll();
        }

        public synchronized void d() {
            notifyAll();
        }

    }

    @Override
    public void A(MyRequest request) throws InterruptedException {
        setSerializer();
        ThreadA a = new ThreadA();
        a.start(); // a執行緒先執行
        a.join(); // 等a執行緒結束
        if (request.getA().length != 0) {
            redisTemplate.opsForList().rightPushAll("listA", request.getA());
        }
    }

    @Override
    public List<String> A_show() throws InterruptedException {
        setSerializer();
        ThreadA_show a_show = new ThreadA_show();
        a_show.start();
        a_show.join();
        return redisTemplate.opsForList().range("listA", 0, -1);
    }

    @Override
    public void B(MyRequest request) throws InterruptedException {
        setSerializer();
        ThreadB b = new ThreadB();
        b.start(); // b執行緒先執行
        b.join(); // 等b執行緒結束
        if (request.getB().length != 0) {
            redisTemplate.opsForList().rightPushAll("listB", request.getB());
        }
    }

    @Override
    public List<String> B_show() throws InterruptedException {
        setSerializer();
        ThreadB_show b_show = new ThreadB_show();
        b_show.start();
        b_show.join();
        return redisTemplate.opsForList().range("listB", 0, -1);
    }

    @Override
    public List<String> C() throws InterruptedException {
        ThreadC c = new ThreadC();
        c.start(); // c執行緒再接著執行
        c.join(); // 等c執行緒結束
        List<String> all_listAB = new ArrayList<>();
        all_listAB.addAll(Objects.requireNonNull(redisTemplate.opsForList().range("listA", 0, -1)));
        all_listAB.addAll(Objects.requireNonNull(redisTemplate.opsForList().range("listB", 0, -1)));
        if(all_listAB.size() > 0){
            redisTemplate.opsForList().rightPushAll("all_listAB", all_listAB);
        }
        return redisTemplate.opsForList().range("all_listAB", 0, -1);
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
