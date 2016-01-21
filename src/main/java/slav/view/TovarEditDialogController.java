package main.java.slav.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.slav.model.Tovar;
import main.java.slav.util.DateUtil;

/**
 * Created by kuharskiy on 21.01.2016.
 */
public class TovarEditDialogController {

    @FXML
    private TextField codeField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField bestBeforeField;


    private Stage dialogStage;
    private Tovar tovar;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the tovar to be edited in the dialog.
     *
     * @param tovar
     */
    public void setTovar(Tovar tovar) {
        this.tovar = tovar;

        codeField.setText(Integer.toString(tovar.getCode()));
        nameField.setText(tovar.getName());
        weightField.setText(Double.toString(tovar.getWeight()));
        priceField.setText(Double.toString(tovar.getPrice()));
        bestBeforeField.setText(DateUtil.format(tovar.getBestBefore()));
        bestBeforeField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            tovar.setCode(Integer.parseInt(codeField.getText()));
            tovar.setName(nameField.getText());
            tovar.setWeight(Double.parseDouble(weightField.getText()));
            tovar.setPrice(Double.parseDouble(priceField.getText()));
            tovar.setBestBefore(DateUtil.parse(bestBeforeField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (codeField.getText() == null || codeField.getText().length() == 0) {
            errorMessage += "������������ ���!\n";
        } else {
            // try to parse the code into an int.
            try {
                Integer.parseInt(codeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "������������ ��� (������ ���� �����)!\n";
            }
        }

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "������������ ��������!\n";
        }

        if (weightField.getText() == null || weightField.getText().length() == 0) {
            errorMessage += "������������ ���!\n";
        } else {
            // try to parse the weight code into an double.
            try {
                Double.parseDouble(weightField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "������������ ��� (������ ���� ������� �����)!\n";
            }
        }

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "������������ ����!\n";
        } else {
            // try to parse the weight code into an double.
            try {
                Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "������������ ���� (������ ���� ������� �����)!\n";
            }
        }

        if (bestBeforeField.getText() == null || bestBeforeField.getText().length() == 0) {
            errorMessage += "������������ ����!\n";
        } else {
            if (!DateUtil.validDate(bestBeforeField.getText())) {
                errorMessage += "������������ ����. ����������� ������ ��.��.����!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("�������� ������ � ����� �����");
            alert.setHeaderText("����������, ������� ���������� ������");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
