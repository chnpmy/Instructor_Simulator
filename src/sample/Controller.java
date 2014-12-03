package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    @FXML MenuItem mitClose;
    @FXML TextArea txtArea;
    @FXML TableColumn tbvMemoryAddress;
    @FXML TableColumn tbvMemoryValue;
    @FXML TableView tbvMemory;
    @FXML TableView tbvRegister;
    @FXML TableColumn tbvRegisterName;
    @FXML TableColumn tbvRegisterValue;
    @FXML Label lblDynamicInstruction;

    @FXML protected void handleCloseAction(ActionEvent event){
        System.exit(0);
    }

    @FXML protected void handleRun(ActionEvent event){
        String s = txtArea.getText();
        try {
            new InstrcutionHandler(s, this).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
