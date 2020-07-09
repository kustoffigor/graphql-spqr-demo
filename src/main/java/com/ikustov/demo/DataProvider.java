package com.ikustov.demo;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.ikustov.demo.model.Author;
import com.ikustov.demo.model.Book;

public class DataProvider {

    private static AtomicInteger authorSeq;
    private static AtomicInteger bookSeq;

    public static final Map<Integer, Author> authorData = new ConcurrentHashMap<Integer, Author>();

    public static final Map<Integer, Book> bookData = new ConcurrentHashMap<Integer, Book>();

    static {
               authorData.put(1, Author.builder().id(1).name("Author 1").build());
        authorData.put(2, Author.builder().id(2).name("Author 2").build());
        authorData.put(3, Author.builder().id(3).name("Author 3").build());

        bookData.put(1, Book.builder().id(1).name("Book 1").author(authorData.get(1)).build());
        bookData.put(2, Book.builder().id(2).name("Book 2").author(authorData.get(2)).build());
        bookData.put(3, Book.builder().id(3).name("Book 3").author(authorData.get(3)).build());
        bookData.put(4, Book.builder().id(4).name("Book 4").author(authorData.get(2)).build());

        authorSeq = new AtomicInteger(authorData.size());
        bookSeq = new AtomicInteger(authorData.size());
    }

    public static int authorSeqNextVal() {
        return authorSeq.incrementAndGet();
    }

    public static int bookSeqNextVal() {
        return bookSeq.incrementAndGet();
    }

    public static String isbn() {
        return UUID.randomUUID().toString();
    }
}
