package main.java.slav; /**
 * Created by kuharskiy on 06.01.2016.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.slav.model.Tovar;
import main.java.slav.view.TovarOverviewController;

import java.io.IOException;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    /**
     * The data as an observable list of Tovars.
     */
    private ObservableList<Tovar> tovarData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        tovarData.add(new Tovar(111, "���� ��������"));
        tovarData.add(new Tovar(112, "����� ��������"));
        tovarData.add(new Tovar(151, "����� ���������"));
        tovarData.add(new Tovar(212, "������� ''��������''"));
        tovarData.add(new Tovar(261, "���� ''�-��-��''"));
    }

    public static void main(String[] args) {
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
        this.primaryStage.setTitle("���������� �� JavaFX");

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
     * ���������� ������ � �������� ������ ��������� �������
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
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}

