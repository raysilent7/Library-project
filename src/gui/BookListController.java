package gui;

import application.Main;
import entities.Book;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.BookService;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BookListController implements Initializable, DataChangeListener {

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
    private TableColumn<Book, Date> tableColumnReleaseDt;

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
        createDialogForm(obj, "/gui/BookForm.fxml", parentStage);
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

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Book> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewBook.setItems(obsList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumnReleaseDt.setCellValueFactory(new PropertyValueFactory<>("release dt."));
        tableColumnImgPath.setCellValueFactory(new PropertyValueFactory<>("img path"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewBook.prefHeightProperty().bind(stage.heightProperty());
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

    @Override
    public void onDataChanged() {
        updateTableView();
    }
}
