package gui;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import services.BookService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSearch;

    @FXML
    public void onMenuItemSearchAction () {
        loadView("/gui/BookList.fxml", (BookListController controller) -> {
            controller.setBookService(new BookService());
            controller.updateTableView();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private synchronized <T> void loadView (String absolutName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            T controller = loader.getController();
            initializingAction.accept(controller);
        }
        catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
