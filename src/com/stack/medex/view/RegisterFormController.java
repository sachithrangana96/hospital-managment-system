package com.stack.medex.view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.stack.medex.db.Database;
import com.stack.medex.dto.UserDto;
import com.stack.medex.enums.AccountType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class RegisterFormController {
    public AnchorPane signUpContext;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public RadioButton rbtnDockter;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText();
       /* for (UserDto dto :Database.userTable){
            if(dto.getEmail().equals(email.trim().toLowerCase())){
                new Alert(Alert.AlertType.WARNING,"Emai Already exists").show();
                return;
            }
        }*/

        Optional<UserDto> selectedUser = Database.userTable.stream().filter(e -> e.getEmail().equals(email.trim().toLowerCase()))
                .findFirst();
        if(selectedUser.isPresent()){
            new Alert(Alert.AlertType.WARNING,"Email is already exist").show();
            return;
        }

        Database.userTable.add(
                new UserDto(
                        txtFirstName.getText(),
                        txtLastName.getText(),
                        email.trim().toLowerCase(),
                        txtPassword.getText(),
                        rbtnDockter.isSelected() ? AccountType.DOCKTER : AccountType.PATIENT
                )
        );
        new Alert(Alert.AlertType.CONFIRMATION,"Welcome").show();
        setUi();
    }

    public void alreadyHaveAnAcountOnAction(ActionEvent actionEvent) throws IOException {
       setUi();
    }


    private  void setUi() throws IOException {
        Stage stage = (Stage) signUpContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.centerOnScreen();
    }
}
