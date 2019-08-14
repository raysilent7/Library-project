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

    public void saveOrUpdate (Book obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove (Book obj) {
        dao.deleteById(obj.getId());
    }
}
