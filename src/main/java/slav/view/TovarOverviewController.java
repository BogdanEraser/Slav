package main.java.slav.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.slav.MainApp;
import main.java.slav.model.Tovar;
import main.java.slav.model.implementation.TovarDAOImpl;
import main.java.slav.util.DateUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by kuharskiy on 12.01.2016.
 */
public class TovarOverviewController {

    @FXML
    private TableView<Tovar> tovarTableView;
    @FXML
    private TableColumn<Tovar, String> nameColumn;
    @FXML
    private TableColumn<Tovar, BigDecimal> priceColumn;

    @FXML
    private Label idLabel;
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
        priceColumn = new TableColumn<Tovar,BigDecimal>("Цена");
        priceColumn.setCellValueFactory(new PropertyValueFactory<Tovar,BigDecimal>("price"));
        */

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

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
            idLabel.setText(Long.toString(tovar.getID()));
            nameLabel.setText(tovar.getName());
            weightLabel.setText(tovar.getWeight().toString());   //Double.toString(tovar.getWeight()));
            priceLabel.setText(tovar.getPrice().toString());    //Double.toString(tovar.getPrice()));
            bestBeforeLabel.setText(DateUtil.format(tovar.getBestBefore()));

        } else {
            // tovar is null, remove all the text.
            idLabel.setText("");
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
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new tovar.
     */
    @FXML
    private void handleNewTovar() throws SQLException, ClassNotFoundException {
        /*Connection conn = DBConnection.OpenDBConnection();
        PreparedStatement st = conn.prepareStatement("SELECT MAX(ID) as MAXID FROM TBLTOVAR");
        ResultSet rs = null;
        try {
            rs = st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Get MAX(ID) failed. Ошибка получения МАКС(ID) для товара");
        }

        Tovar tempTovar = null;
        if (rs.next()) {
           tempTovar = new Tovar(rs.getInt("MAXID")+1);
        }
        else {tempTovar = new Tovar(0);}

        rs.close();
        st.close();
        conn.close();*/

        Tovar tempTovar = new Tovar();
        boolean okClicked = mainApp.showTovarEditDialog(tempTovar);
        if (okClicked) {
            mainApp.getTovarData();//.add(tempTovar);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected tovar.
     */
    @FXML
    private void handleEditTovar() {

        Tovar selectedTovar = tovarTableView.getSelectionModel().getSelectedItem();
        if (selectedTovar != null) {
            boolean okClicked = mainApp.showTovarEditDialog(selectedTovar);
            if (okClicked) {
                showTovarDetails(selectedTovar);
            }

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


    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteTovar() {
        long selectedIndex = tovarTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Вопрос");
            alert.setHeaderText("Удаление");
            alert.setContentText("Действительно удалить товар под кодом " + mainApp.getTovarData().get((int) selectedIndex).getID() + " ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                TovarDAOImpl tt = new TovarDAOImpl();
                try {
                    if (tt.deleteTovar(mainApp.getTovarData().get((int) selectedIndex).getID())) {
                        mainApp.getTovarData();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //tovarTableView.getItems().remove(selectedIndex);
            }
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
