package gui;

import entities.Book;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.BookService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

public class BookListController {

    private BookService service;

    @FXML
    private TableView<Book> tableViewBook;

    @FXML
    private TableColumn<Book, Integer> tableColumnIsbn;

    @FXML
    private TableColumn<Book, String> tableColumnName;

    @FXML
    private TableColumn<Book, String> tableColumnAutor;

    @FXML
    private TableColumn<Book, Double> tableColumnPrice;

    @FXML
    private TableColumn<Book, Date> tableColumnDtPublicacao;

    @FXML
    private TableColumn<Book, Path> tableColumnImgPath;

    @FXML
    private TableColumn<Book, Book> tableColumnEdit;

    @FXML
    private TableColumn<Book, Book> tableColumnRemove;

    @FXML
    private Button btSearch;

    @FXML
    private Button btNew;

    @FXML
    private TextField txtSearchField;

    private ObservableList<Book> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Book obj = new Book();
        createDialogForm(obj, "/gui/BookForm.fxml", parentStage)
    }

    @FXML
    public void onBtSearchAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Book obj = new Book();
        createDialogForm(obj, "/gui/BookForm.fxml", parentStage);
    }

    public void setBookService (BookService service) {
        this.service = service;
    }

    private void createDialogForm(Book obj, String absolutName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            Pane pane = loader.load();

            BookFormController controller = loader.getController();
            controller.setBook(obj);
            controller.setBookService(new BookService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Book data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
