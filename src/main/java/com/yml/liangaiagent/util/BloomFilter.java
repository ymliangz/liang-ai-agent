package com.yml.liangaiagent.util;

import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloomFilter {

    @Resource
    private RedissonClient redissonClient;

    // 获取或创建一个布隆过滤器
    private RBloomFilter<Object> getBloomFilter(String filterName) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(filterName);

        // 如果过滤器不存在，则初始化
        // 参数1：预期插入的元素总量（比如100万）
        // 参数2：期望的误判率（比如1%）
        if (!bloomFilter.isExists()) {
            bloomFilter.tryInit(1000000L, 0.01);
        }
        return bloomFilter;
    }

    // 向过滤器添加元素
    public void add(String filterName, Object value) {
        RBloomFilter<Object> bloomFilter = getBloomFilter(filterName);
        bloomFilter.add(value);
    }

    // 判断元素是否可能存在
    public boolean mightContain(String filterName, Object value) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(filterName);
        return bloomFilter.contains(value);
    }
}
