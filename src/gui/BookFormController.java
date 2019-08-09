package gui;

import db.DbException;
import entities.Book;
import exceptions.ValidationException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.BookService;

import java.net.URL;
import java.util.*;

public class BookFormController implements Initializable {

    private Book entity;
    private BookService service;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtReleaseDt;

    @FXML
    private TextField txtImgPath;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setBook (Book entity) {
        this.entity = entity;
    }

    public void setBookService(BookService service) {
        this.service = service;
    }

    @FXML
    public void onBtSaveAction (ActionEvent event) {
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
        catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        }
    }

    @FXML
    public void onBtCancelAction (ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void subscribeDataChangeListener (DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    public void updateFormData () {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }

        //falta arrumar um jeito de converter a data
        txtIsbn.setText(String.valueOf(entity.getIsbn()));
        txtName.setText(entity.getName());
        txtAutor.setText(entity.getAutorName());
        txtPrice.setText(String.valueOf(entity.getPrice()));
        txtReleaseDt.setText(String.valueOf(entity.getName()));
        txtImgPath.setText(String.valueOf(entity.getName()));
    }

    private Book getFormData() {
        Book obj = new Book ();

        //Falta validacao de dados (nome, autor, data e path)
        obj.setIsbn(Utils.tryParseToInt(txtIsbn.getText()));
        obj.setName(txtName.getText());
        obj.setAutorName(txtAutor.getText());
        obj.setPrice(Utils.tryParseToDouble(txtName.getText()));
        obj.setReleaseDt(Date.parse(txtName.getText()));
        obj.setName(txtName.getText());

        return obj;
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private void setErrorMessages (Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("Name")) {
            labelErrorName.setText(errors.get("Name"));
        }
    }
}
