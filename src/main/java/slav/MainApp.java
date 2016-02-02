package main.java.slav; /**
 * Created by kuharskiy on 06.01.2016.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.slav.model.Tovar;
import main.java.slav.model.implementation.TovarDAOImpl;
import main.java.slav.view.TovarEditDialogController;
import main.java.slav.view.TovarOverviewController;

import java.io.IOException;
import java.sql.SQLException;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    /**
     * The data as an observable list of Tovars.
     */
    private ObservableList<Tovar> tovarData = FXCollections.observableArrayList();
    //private static ObservableList<Tovar> tovarData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
       /* tovarData.add(new Tovar(111, "Хлеб формовой",1d,"","",1d,1));
        tovarData.add(new Tovar(112, "Батон нарезной",2d,"",""2d,1));
        tovarData.add(new Tovar(151, "Булка городская",3d,"","",3d,2));
        tovarData.add(new Tovar(212, "Печенье ''Марцелик''",4d,"","",4d,3));
        tovarData.add(new Tovar(261, "Торт ''У-ла-ла''",5d,"","",5d,4));
        */
        TovarDAOImpl tt = new TovarDAOImpl();
        try {
            tovarData.addAll(tt.getAllTovars());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * MAIN ROUTINE
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Preparing some data
        TovarDAOImpl tt = new TovarDAOImpl();
        System.out.println(tt.getTovarByID(1));
        System.out.println(tt.getTovarByID(2));
        System.out.println(tt.getTovarByID(3));

        //Launching JavaFX
        launch(args);
    }

    /**
     * Returns the data as an observable list of Tovars.
     *
     * @return
     */
    public ObservableList<Tovar> getTovarData() {
        return tovarData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Славяночка на JavaFX");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/1453494113_Shop.png"));
        initRootLayout();

        showTovarCard();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * показываем лэйаут с товарами внутри корневого лэйаута
     */
    public void showTovarCard() {
        try {
            // Load tovar card.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TovarCard.fxml"));
            AnchorPane personOverview = loader.load();

            // Set tovar card into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            TovarOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified tovar. If the user
     * clicks OK, the changes are saved into the provided tovar object and true
     * is returned.
     *
     * @param tovar the tovar object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showTovarEditDialog(Tovar tovar) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TovarEdit.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование товара");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the tovar into the controller.
            TovarEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTovar(tovar);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}

