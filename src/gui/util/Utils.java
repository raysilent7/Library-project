package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    private static DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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

    public static Date tryParseToUtilDate (String date) {
        try {
            Date utilDate = sdf.parse(date);
            return utilDate;
        } catch (ParseException e) {
            return null;
        }
    }

    public static java.sql.Date tryParseToSqlDate (Date date) {
        TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");
        TimeZone.setDefault(timeZone);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }
}
