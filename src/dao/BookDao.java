package dao;

import entities.Book;

import java.util.List;

public interface BookDao {

    void insert(Book obj);
    void update(Book obj);
    void deleteByIsbn(Integer id);
    Book findByIsbn(Integer id);
    List<Book> findByAny (Book obj);
    List<Book> findAll();
}
