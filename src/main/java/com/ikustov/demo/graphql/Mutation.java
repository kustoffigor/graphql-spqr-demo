package com.ikustov.demo.graphql;

import com.ikustov.demo.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikustov.demo.model.Author;
import com.ikustov.demo.model.Book;
import com.ikustov.demo.repository.AuthorRepository;
import com.ikustov.demo.repository.BookRepository;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class Mutation {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GraphQLMutation(name = "addAuthor")
    public Author addAuthor(String name) {
        return authorRepository.save(Author.builder()
                .id(DataProvider.authorSeqNextVal())
                .name(name)
                .build());
    }

    @GraphQLMutation(name = "addBook")
    public Book addBook(String name, int authorId) {
        return bookRepository.save(Book.builder()
                .id(DataProvider.bookSeqNextVal())
                .name(name)
                .author(authorRepository.findById(authorId))
                .build());
    }

    @GraphQLMutation(name = "deleteAuthor")
    public boolean deleteAuthor(int id) {
        return authorRepository.delete(id);
    }

    @GraphQLMutation(name = "deleteBook")
    public boolean deleteBook(int id) {
        return bookRepository.delete(id);
    }
}
