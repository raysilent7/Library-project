package dao.impl;

import dao.BookDao;
import db.DB;
import db.DbException;
import db.DbIntegrityException;
import entities.Book;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJdbc implements BookDao {

    private Connection conn;

    public BookDaoJdbc (Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert (Book book) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO book (Isbn, Name, Autor, Price, ReleaseDate, ImgPath) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, book.getIsbn());
            st.setString(2, book.getName());
            st.setString(3, book.getAutorName());
            st.setDouble(4, book.getPrice());
            st.setTimestamp(5, new Timestamp(book.getReleaseDt().getTime()));
            st.setString(6, String.valueOf(book.getImgPath()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    book.setIsbn(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error: No rows affected.");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update (Book book) {
        PreparedStatement st = null;
        Timestamp timestamp = null;
        try {
            st = conn.prepareStatement("UPDATE book SET Isbn = ?, " +
                    "Name = ?, Autor = ?, Price = ?, ReleaseDate = ?, " +
                    "ImgPath = ? where Id = ?");
            st.setInt(1, book.getIsbn());
            st.setString(2, book.getName());
            st.setString(3, book.getAutorName());
            st.setDouble(4, book.getPrice());
            st.setTimestamp(5, new Timestamp(book.getReleaseDt().getTime()));
            st.setString(6, String.valueOf(book.getImgPath()));
            st.setInt(7, book.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById (Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM book WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Book findById (Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM book WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Book book = instantiateBook(rs);
                return book;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Book> findByAny(Book book) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * " +
                    "FROM book " +
                    "WHERE Isbn = ? or Name = ? or Autor = ? or Price = ? or ReleaseDate = ? or ImgPath = ? " +
                    "ORDER BY id");
            st.setInt(1, book.getIsbn());
            st.setString(2, book.getName());
            st.setString(3, book.getAutorName());
            st.setDouble(4, book.getPrice());
            st.setTimestamp(5, new Timestamp(book.getReleaseDt().getTime()));
            st.setString(6, String.valueOf(book.getImgPath()));

            rs = st.executeQuery();

            List<Book> list = new ArrayList<>();

            while (rs.next()) {

                Book obj = instantiateBook(rs);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Book> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM book ORDER BY id");

            rs = st.executeQuery();

            List<Book> list = new ArrayList<>();

            while (rs.next()) {

                Book book = instantiateBook(rs);
                list.add(book);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Book instantiateBook (ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("Id"));
        book.setIsbn(rs.getInt("Isbn"));
        book.setName(rs.getString("Name"));
        book.setAutorName(rs.getString("Autor"));
        book.setPrice(rs.getDouble("Price"));
        book.setReleaseDt(rs.getDate("ReleaseDate"));
        book.setImgPath(Path.of(rs.getString("ImgPath")));
        return book;
    }
}
