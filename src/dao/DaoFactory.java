package dao;

import db.DB;

public class DaoFactory {

    public static BookDao createBookDao () {
        return new BookDaoJdbc (DB.openConnection());
    }
}
