package entities;

import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

public class Book {

    private Integer isbn;
    private String name;
    private String autorName;
    private Double price;
    private Date dtPublicacao;
    private Path imgPath;

    public Book() {
    }

    public Book(Integer isbn, String name, String autorName, Double price, Date dtPublicacao, Path imgPath) {
        this.isbn = isbn;
        this.name = name;
        this.autorName = autorName;
        this.price = price;
        this.dtPublicacao = dtPublicacao;
        this.imgPath = imgPath;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutorName() {
        return autorName;
    }

    public void setAutorName(String autorName) {
        this.autorName = autorName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDtPublicacao() {
        return dtPublicacao;
    }

    public void setDtPublicacao(Date dtPublicacao) {
        this.dtPublicacao = dtPublicacao;
    }

    public Path getImgPath() {
        return imgPath;
    }

    public void setImgPath(Path imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getIsbn().equals(book.getIsbn()) &&
                getName().equals(book.getName()) &&
                getAutorName().equals(book.getAutorName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getName(), getAutorName());
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", name='" + name + '\'' +
                ", autorName='" + autorName + '\'' +
                ", price=" + price +
                ", dtPublicacao=" + dtPublicacao +
                ", imgPath=" + imgPath +
                '}';
    }
}
