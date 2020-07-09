package com.ikustov.demo.graphql;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ikustov.demo.exception.QueryNotFoundException;
import com.ikustov.demo.model.Author;
import com.ikustov.demo.model.Book;
import com.ikustov.demo.model.MissingPage;
import com.ikustov.demo.repository.AuthorRepository;
import com.ikustov.demo.repository.BookRepository;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.execution.ResolutionEnvironment;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class Query {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GraphQLQuery(name = "allBooks")
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @GraphQLQuery(name = "allAuthors")
    public List<Author> allAuthors() {
        return authorRepository.findAll();
    }

    @GraphQLQuery(name = "book")
    public Book book(@GraphQLArgument(name = "id") int id) {
        Book r = bookRepository.findById(id);
        if (r == null) {
            throw new QueryNotFoundException("Book not found.");
        }
        return r;
    }

    @GraphQLQuery(name = "author")
    public Author author(@GraphQLArgument(name = "id") int id) {
        Author r = authorRepository.findById(id);
        if (r == null) {
            throw new QueryNotFoundException("Author not found.");
        }
        return r;
    }

    @GraphQLQuery(name = "countBooks")
    public int countBooks() {
        return bookRepository.count();
    }

    @GraphQLQuery(name = "countAuthors")
    public int countAuthors() {
        return authorRepository.count();
    }

    @GraphQLQuery
    public CompletableFuture<List<MissingPage>> missingPages(
            @GraphQLContext Book book,
            @GraphQLEnvironment ResolutionEnvironment environment) {
        DataLoader<Integer, List<MissingPage>> loader =  environment.dataFetchingEnvironment.getDataLoader("page");
        return loader.load(book.getId());
    }
}
