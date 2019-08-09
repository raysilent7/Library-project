package gui;

import entities.Book;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.BookService;

public class BookListController {

    private BookService service;

    @FXML
    private TableView<Book> tableViewDepartment;

    @FXML
    private TableColumn<Book, Integer> tableColumnId;

    @FXML
    private TableColumn<Book, String> tableColumnName;

    @FXML
    private TableColumn<Book, Book> tableColumnEdit;

    @FXML
    private TableColumn<Book, Book> tableColumnRemove;

    @FXML
    private Button btNew;

    private ObservableList<Book> obsList;
}
