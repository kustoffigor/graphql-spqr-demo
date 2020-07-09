package com.ikustov.demo.repository;

import com.ikustov.demo.model.MissingPage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
public class PageRepository {
    public List<MissingPage> findByBookId(int bookId) {
        return new Random(bookId).ints(10, 10, 1000)
                .boxed()
                .map(number -> new MissingPage(number, bookId))
            .collect(Collectors.toList());
}

    // Batch выборка
    public List<MissingPage> findByBookIdsIn(List<Integer> bookIds) {
        System.out.println("find by missing pages method called with args: " + bookIds);
        List<MissingPage> result = new ArrayList<>();
        for (Integer bookId : bookIds) {
            result.addAll(findByBookId(bookId));
        }
        return result;
    }
}