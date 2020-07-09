package com.ikustov.demo.graphql;

import com.ikustov.demo.DataProvider;
import com.ikustov.demo.model.Book;
import com.ikustov.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class BookResolver {
    @GraphQLQuery
    public String isbn(@GraphQLContext Book book) {
        System.out.println("isbn called with arg: " + book.getId());
        return DataProvider.isbn();
    }
}
