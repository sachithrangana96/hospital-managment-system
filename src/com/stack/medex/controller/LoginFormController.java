package com.stack.medex.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.stack.medex.db.Database;
import com.stack.medex.dto.UserDto;
import com.stack.medex.enums.AccountType;
import com.stack.medex.util.Cookie;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public RadioButton rDtnDockter;
    public AnchorPane loginArea;
    public AnchorPane loginContext;


    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        AccountType accountType= rDtnDockter.isSelected() ? AccountType.DOCKTER : AccountType.PATIENT;

      

        for (UserDto dto:Database.userTable){
            if(dto.getEmail().trim().toLowerCase().equals(email)){
                if(dto.getPassword().equals(password)){
                    if(dto.getAccountType().equals(accountType)){
                        new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
                        Cookie.selectedUser = dto;
                        setUi("DocterDashboardForm");
                        return;
                    }else{
                       /* new Alert(Alert.AlertType.WARNING,"We Can,t find Your "+accountType+"Account")  */

                        new Alert(Alert.AlertType.WARNING,String.format("We can 't find your (%s) Account",accountType.name())).show();
                        return;
                    }
                }else{
                    new Alert(Alert.AlertType.WARNING,"Your Password is incorrect").show();
                    return;
                }
            }
        }
        new Alert(Alert.AlertType.WARNING,String.format("We can't find an email (%s )",email)).show();
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {

        setUi("RegisterForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
