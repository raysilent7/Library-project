package gui;

import db.DbException;
import entities.Book;
import exceptions.ValidationException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
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
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookFormController implements Initializable {

    private Book entity;
    private BookService service;
    private DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

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
        initializeNodes();
    }

    public void subscribeDataChangeListener (DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    public void updateFormData () {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtIsbn.setText(String.valueOf(entity.getIsbn()));
        txtName.setText(entity.getName());
        txtAutor.setText(entity.getAutorName());
        txtPrice.setText(String.valueOf(entity.getPrice()));
        txtReleaseDt.setText(sdf.format(entity.getReleaseDt()));
        txtImgPath.setText(String.valueOf(entity.getImgPath()));
    }

    private Book getFormData() {
        Book obj = new Book ();
        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));
        obj.setIsbn(Utils.tryParseToInt(txtIsbn.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("Name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        if (txtAutor.getText() == null || txtAutor.getText().trim().equals("")) {
            exception.addError("Autor", "Field can't be empty");
        }
        obj.setAutorName(txtAutor.getText());

        obj.setPrice(Utils.tryParseToDouble(txtPrice.getText()));
        obj.setReleaseDt(Utils.tryParseToUtilDate(txtReleaseDt.getText()));
        obj.setImgPath(Path.of(txtImgPath.getText()));

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return obj;
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private void initializeNodes () {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldInteger(txtIsbn);
        Constraints.setTextFieldMaxLength(txtIsbn, 6);
        Constraints.setTextFieldMaxLength(txtName, 40);
        Constraints.setTextFieldMaxLength(txtAutor, 30);
        Constraints.setTextFieldDouble(txtPrice);
        Constraints.setTextFieldMaxLength(txtImgPath, 60);
    }

    private void setErrorMessages (Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("Name")) {
            labelErrorName.setText(errors.get("Name"));
        }
    }
}
