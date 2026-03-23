package com.yml.liangaiagent.util;


import jakarta.annotation.Resource;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DLock {

    @Resource
    private RedissonClient redissonClient;

    public void lock() {
        RLock lock = redissonClient.getLock("myLock");
        try {
            // 加锁：如果锁被占用，会一直阻塞等待，直到获取锁
            lock.lock();

            // ========== 执行核心业务逻辑 ==========
            System.out.println("业务逻辑正在执行...");
            // ===================================

        } finally {
            // 释放锁：务必在 finally 中释放，避免死锁
            lock.unlock();
        }
    }

    public void tryLock() {
        RLock lock = redissonClient.getLock("myLock");
        boolean isLocked = false;
        try {
            // 尝试加锁：
            // - 最多等待 5 秒（等待锁释放）
            // - 锁自动释放时间为 10 秒（到期自动删除，无需 Watchdog）
            isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);

            if (isLocked) {
                // 成功获取锁，执行业务
                System.out.println("成功获取锁");
            } else {
                // 5秒内没拿到锁，执行降级逻辑
                System.out.println("获取锁失败，稍后重试");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("线程被中断");
        } finally {
            // 释放锁前，需判断锁是否仍被当前线程持有
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public void other() {
        // 1. 公平锁：按请求顺序排队
        RLock fairLock = redissonClient.getFairLock("fairLock");

        // 2. 联锁：同时锁定多个资源（相当于一次锁住多把锁）
        RLock lock1 = redissonClient.getLock("lock1");
        RLock lock2 = redissonClient.getLock("lock2");
        RedissonMultiLock multiLock = new RedissonMultiLock(lock1, lock2);

        // 3. 读写锁
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("rwLock");
        rwLock.readLock().lock();   // 读锁
        rwLock.writeLock().lock();  // 写锁
    }
}
