package com.yml.liangaiagent.util;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BloomFilterTest {

    @Resource
    BloomFilter bloomFilter;

    @Test
    public void test(){
        String filterName = "user:filter";
        // 添加元素
        bloomFilter.add(filterName, "user:1001");

        // 判断是否存在
        System.out.println(bloomFilter.mightContain(filterName, "user:1001")); // 大概率输出 true
        System.out.println(bloomFilter.mightContain(filterName, "user:2000")); // 大概率输出 false
    }
}