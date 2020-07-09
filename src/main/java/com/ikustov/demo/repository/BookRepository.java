package com.ikustov.demo.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ikustov.demo.DataProvider;
import com.ikustov.demo.model.Book;

@Repository
public class BookRepository {

    public Book findById(int id) {
        return DataProvider.bookData.get(id);
    }

    public List<Book> findAll() {
        return DataProvider.bookData.values().stream().collect(Collectors.toList());
    }

    public int count() {
        return DataProvider.bookData.size();
    }

    public Book save(Book book) {
        int id = DataProvider.bookSeqNextVal();
        book.setId(id);
        DataProvider.bookData.put(id, book);
        return book;
    }

    public boolean delete(int id) {
        if (DataProvider.bookData.containsKey(id)) {
            DataProvider.bookData.remove(id);
            return true;
        } else {
            return false;
        }
    }
}
