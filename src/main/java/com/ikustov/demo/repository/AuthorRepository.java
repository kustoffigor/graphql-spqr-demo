package com.ikustov.demo.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ikustov.demo.DataProvider;
import com.ikustov.demo.model.Author;

@Repository
public class AuthorRepository {

    public Author findById(int id) {
        return DataProvider.authorData.get(id);
    }

    public List<Author> findAll() {
        return DataProvider.authorData.values().stream().collect(Collectors.toList());
    }

    public int count() {
        return DataProvider.authorData.size();
    }

    public Author save(Author author) {
        int id = DataProvider.authorSeqNextVal();
        author.setId(id);
        DataProvider.authorData.put(id, author);
        return author;
    }

    public boolean delete(int id) {
        if (DataProvider.authorData.containsKey(id)) {
            DataProvider.authorData.remove(id);
            return true;
        } else {
            return false;
        }
    }
}
