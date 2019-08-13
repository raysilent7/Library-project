package services;

import dao.BookDao;
import dao.DaoFactory;
import db.DbIntegrityException;
import entities.Book;

import java.util.List;

public class BookService {

    private BookDao dao = DaoFactory.createBookDao();

    public List<Book> findAll() {

        return dao.findAll();
    }

    public List<Book> findByAny (Book obj) {

        return dao.findByAny(obj);
    }

    public void save (Book obj) {
        if (obj.getIsbn() == null) {
            throw new DbIntegrityException("ISBN can't be null");
        }
        else {
            dao.insert(obj);
        }
    }

    public void remove (Book obj) {
        dao.deleteByIsbn(obj.getIsbn());
    }
}
