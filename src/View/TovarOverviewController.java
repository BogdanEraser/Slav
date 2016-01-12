package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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


}
