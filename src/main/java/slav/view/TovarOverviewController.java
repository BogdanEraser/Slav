package main.java.slav.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.slav.MainApp;
import main.java.slav.model.Tovar;
import main.java.slav.util.DateUtil;

/**
 * Created by kuharskiy on 12.01.2016.
 */
public class TovarOverviewController {

    @FXML
    private TableView<Tovar> tovarTableView;
    @FXML
    private TableColumn<Tovar, String> nameColumn;
    @FXML
    private TableColumn<Tovar, Double> priceColumn;

    @FXML
    private Label codeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label bestBeforeLabel;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TovarOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the Tovar table with the two columns.

        /* WITHOUT LAMBDA
        nameColumn = new TableColumn<Tovar,String>("Наименование");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Tovar,String>("name"));
        priceColumn = new TableColumn<Tovar,Double>("Цена");
        priceColumn.setCellValueFactory(new PropertyValueFactory<Tovar,Double>("price"));
        */

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        // Clear tovar details.
        showTovarDetails(null);

        // Listen for selection changes and show the tovar details when changed.
        tovarTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTovarDetails(newValue));

    }


    /**
     * Fills all text fields to show details about the tovar.
     * If the specified tovar is null, all text fields are cleared.
     *
     * @param tovar the tovar or null
     */
    private void showTovarDetails(Tovar tovar) {
        if (tovar != null) {
            // Fill the labels with info from the person object.
            codeLabel.setText(Integer.toString(tovar.getCode()));
            nameLabel.setText(tovar.getName());
            weightLabel.setText(Double.toString(tovar.getWeight()));
            priceLabel.setText(Double.toString(tovar.getPrice()));
            bestBeforeLabel.setText(DateUtil.format(tovar.getBestBefore()));

        } else {
            // tovar is null, remove all the text.
            codeLabel.setText("");
            nameLabel.setText("");
            weightLabel.setText("");
            priceLabel.setText("");
            bestBeforeLabel.setText("");
        }
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        tovarTableView.setItems(mainApp.getTovarData());
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteTovar() {
        int selectedIndex = tovarTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tovarTableView.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ничего не выбрано");
            alert.setContentText("Пожалуйста, выберите строку.");
            alert.showAndWait();
        }
    }
}
