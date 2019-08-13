package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Stage currentStage (ActionEvent event) {
        return (Stage)((Node)event.getSource()).getScene().getWindow();
    }

    public static Integer tryParseToInt (String str) {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double tryParseToDouble (String str) {
        try {
            return Double.parseDouble(str);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public static String testFieldEmpty (String str) {
        if (str == null || str.trim().equals("")) {
            return str;
        }
        else {
            return null;
        }
    }

    private static String formatData(Date data) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String newDate = dateFormat.format(data);
        return newDate;
    }
}
