package main.java.slav.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import main.java.slav.MainApp;
import main.java.slav.model.Tovar;
import main.java.slav.model.implementation.TovarDAOImpl;
import main.java.slav.util.ExcelUtils;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by Eraser on 04.02.2016.
 */
public class MenuController {
    @FXML
    private MenuItem import24exp2tt;

    @FXML
    private MenuItem update24exp2tt;

    @FXML
    private MenuItem export24exp2tt;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MenuController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Listen for menu action.

        /* WITHOUT LAMBDA
        import4exp2tt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("Opening Database Connection...");
            }
        });
*/

        import24exp2tt.setOnAction((event) -> {
            ArrayList<Tovar> tovarArrayList = ExcelUtils.getTovarFromExcel("D://Dropbox//24exp2tt.xlsx", "import");
            TovarDAOImpl tt = new TovarDAOImpl();
            try {
                if (tt.mergeTovarBatch(tovarArrayList)) {
                    // Update OK
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Все хорошо");
                    alert.setHeaderText("Импорт прошел успешно");
                    alert.showAndWait();
                } else {
                    // Nothing updated
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Импорт данных неудался");
                    alert.showAndWait();
                }
                //mainApp.getTovarData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        export24exp2tt.setOnAction((event) -> {
            TovarDAOImpl tt = new TovarDAOImpl();
            try {
                if (ExcelUtils.exportDataToExcel("D://Dropbox//24exp2tt.xlsx", "export", tt.getAllTovars())) {
                    // Update OK
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Все хорошо");
                    alert.setHeaderText("Экспорт прошел успешно");
                    alert.showAndWait();
                } else {
                    // Nothing updated
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Экспорт неудался");
                    alert.showAndWait();
                }
                //mainApp.getTovarData();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        update24exp2tt.setOnAction((event) -> {
            TovarDAOImpl tt = new TovarDAOImpl();
            try {
                if (ExcelUtils.updateDataInExcel("D://Dropbox//24exp2tt.xlsx", "export", tt.getAllTovars())) {
                    // Update OK
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Все хорошо");
                    alert.setHeaderText("Обновление прошло успешно");
                    alert.showAndWait();
                } else {
                    // Nothing updated
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Обновление неудалось");
                    alert.showAndWait();
                }
                //mainApp.getTovarData();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

    }

}
