package dao;

import dao.impl.BookDaoJdbc;
import db.DB;

public class DaoFactory {

    public static BookDao createBookDao () {
        return new BookDaoJdbc(DB.openConnection());
    }
}
