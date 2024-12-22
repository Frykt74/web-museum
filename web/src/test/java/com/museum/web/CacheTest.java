package com.museum.web;

import com.museum.web.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.IntStream;

@SpringBootTest
public class CacheTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void testCachePerformance() {
        int testIterations = 100;

        long cacheTime = IntStream.range(0, testIterations)
                .mapToLong(i -> {
                    long start = System.nanoTime();
                    categoryService.findAll();
                    return System.nanoTime() - start;
                })
                .sum() / testIterations;

        System.out.println("Среднее время без кеша: " + cacheTime / 1_000_000 + " мс");
    }
}
