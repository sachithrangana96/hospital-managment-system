package com.stack.medex.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.stack.medex.db.DBconnection;
import com.stack.medex.db.Database;
import com.stack.medex.dto.User;
import com.stack.medex.enums.AccountType;
import com.stack.medex.util.CrudUtil;
import com.stack.medex.util.IdGenerate;
import com.stack.medex.util.PasswordConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        Optional<User> selectedUser = Database.userTable.stream().filter(e -> e.getEmail().equals(email.trim().toLowerCase()))
                .findFirst();
        if(selectedUser.isPresent()){
            new Alert(Alert.AlertType.WARNING,"Email is already exist").show();
            return;
        }

//        Database.userTable.add(
//                new User(
//                        txtFirstName.getText(),
//                        txtLastName.getText(),
//                        email.trim().toLowerCase(),
//                        new PasswordConfig().encrypt( txtPassword.getText()),
//                        rbtnDockter.isSelected() ? AccountType.DOCKTER : AccountType.PATIENT
//                )
//        );



        User user = new User(
                txtFirstName.getText(),
                txtLastName.getText(),
                email.trim().toLowerCase(),
                new PasswordConfig().encrypt( txtPassword.getText()),
                rbtnDockter.isSelected() ? AccountType.DOCTOR : AccountType.PATIENT
        );

        try {

            boolean isSaved = CrudUtil.execute(
                    "INSERT INTO user VALUES(?,?,?,?,?,?)",
                    new IdGenerate().generatedId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAccountType().name()

            );

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                setUi();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

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
