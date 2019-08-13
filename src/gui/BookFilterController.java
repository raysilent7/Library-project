package gui;

import db.DbException;
import entities.Book;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.BookService;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookFilterController implements Initializable {

    private String filterOption;
    private Book entity;
    private BookService service;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private Button btFilter;

    @FXML
    private Button btCancel;

    @FXML
    private TextField txtFilterField;

    @FXML
    private ComboBox<String> filterOptionsComboBox;

    private ObservableList<String> obsList;

    public void setBook (Book entity) {
        this.entity = entity;
    }

    public void setBookService(BookService service) {
        this.service = service;
    }

    @FXML
    public void onBtFilterAction (ActionEvent event) {
        try {
            filterOptionsComboBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event2) {
                    filterOption = filterOptionsComboBox.getSelectionModel().getSelectedItem();
                }
            });
            entity = getFormData();
            service.findByAny(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch (DbException e) {
            Alerts.showAlert("Error finding object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
        Utils.currentStage(event).close();
    }

    @FXML
    public void onBtCancelAction (ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> list = new ArrayList<>();
        list.add("Isbn");
        list.add("Name");
        list.add("Autor");
        list.add("Price");
        list.add("Release Date");
        list.add("Image");

        obsList = FXCollections.observableArrayList(list);
        filterOptionsComboBox.setItems(obsList);
    }

    public void subscribeDataChangeListener (DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    public void updateFormData () {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }

        txtFilterField.setText(String.valueOf(entity.getIsbn()));
    }

    private Book getFormData() {
        Book obj = new Book ();

        if (filterOption.equals("Isbn")) {
            obj.setIsbn(Utils.tryParseToInt(txtFilterField.getText()));
        } else if (filterOptionsComboBox.getValue().equals("Name")) {
            obj.setName(txtFilterField.getText());
        } else if (filterOptionsComboBox.getValue().equals("Autor")) {
            obj.setAutorName(txtFilterField.getText());
        } else if (filterOptionsComboBox.getValue().equals("Price")) {
            obj.setPrice(Utils.tryParseToDouble(txtFilterField.getText()));
        } else if (filterOptionsComboBox.getValue().equals("Release Date")) {
            obj.setReleaseDt(Date.valueOf(txtFilterField.getText()));
        }else if (filterOptionsComboBox.getValue().equals("Image")) {
            obj.setName(txtFilterField.getText());
        }

        return obj;
    }
}
