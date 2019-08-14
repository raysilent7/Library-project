package gui;

import application.Main;
import entities.Book;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    private ObservableList<Book> obsList;
    private BookService service = new BookService();

    @FXML
    private MenuItem menuItemSearch;

    @FXML
    public void onMenuItemSearchAction () {
        loadView("/gui/BookList.fxml", (BookListController controller) -> {
            controller.setBookService(new BookService());
            List<Book> list = service.findAll();
            obsList = FXCollections.observableArrayList(list);
            controller.updateTableView(obsList);
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
