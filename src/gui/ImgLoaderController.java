package gui;

import entities.Book;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImgLoaderController implements Initializable {

    private Book entity;

    @FXML
    private ImageView bookImage;

    public void loadImage () {
        try {
            bookImage.setImage(new Image(new FileInputStream(String.valueOf(entity.getImgPath()))));
        } catch (FileNotFoundException e) {
            Alerts.showAlert("Error loading image", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void setBook(Book obj) {
        entity = obj;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
