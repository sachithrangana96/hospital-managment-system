package com.stack.medex.controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.stack.medex.db.Database;
import com.stack.medex.dto.DocktorDto;
import com.stack.medex.dto.User;
import com.stack.medex.enums.GenderType;
import com.stack.medex.util.Cookie;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DoctorRegistrationFormController {
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtNic;
    public TextField txtContact;
    public TextField txtEmail;
    public JFXRadioButton rbtnMale;
    public TextField txtSpecillization;
    public JFXTextArea txtAddress;
    public AnchorPane doctorRegistrationContext;
    public Button btnSubmit;


    public  void initialize(){

        loadUserData();
        txtNic.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(Database.doctorTable.stream().filter(e->e.getNic().equals(newValue)).findFirst().isPresent()){
                new Alert(Alert.AlertType.WARNING,"Nic Conflict").show();
                btnSubmit.setDisable(true);
                return;
            }
            btnSubmit.setDisable(false);
        }));
    }

    private void loadUserData() {
        User selectedUser = Cookie.selectedUser;
        txtFirstName.setText(selectedUser.getFirstName());
        txtLastName.setText(selectedUser.getLastName());
        txtEmail.setText(selectedUser.getEmail());
    }

    public void submitDataOnAction(ActionEvent actionEvent) {

        if(Database.doctorTable.stream().filter(e->e.getNic().equals(txtNic.getText().trim())).findFirst().isPresent()){
            new Alert(Alert.AlertType.WARNING,"Nic Conflict").show();
            btnSubmit.setDisable(true);
            txtNic.setStyle("-fx-border-color: red");
            return;
        }

        DocktorDto docktorDto = new DocktorDto(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtNic.getText(),
                txtContact.getText(),
                txtEmail.getText(),
                txtSpecillization.getText(),
                txtAddress.getText(),
                rbtnMale.isSelected()? GenderType.MALE:GenderType.FEMALE

        );
        Database.doctorTable.add(docktorDto);
        Stage stage = (Stage) doctorRegistrationContext.getScene().getWindow();
        stage.close();
    }
}
