package com.stack.medex.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.stack.medex.db.DBconnection;
import com.stack.medex.db.Database;
import com.stack.medex.dto.User;
import com.stack.medex.enums.AccountType;
import com.stack.medex.util.Cookie;
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
import java.sql.*;

public class LoginFormController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public RadioButton rDtnDockter;
    public AnchorPane loginArea;
    public AnchorPane loginContext;


    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        AccountType accountType= rDtnDockter.isSelected() ? AccountType.DOCTOR : AccountType.PATIENT;


        try {


            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE email=? AND account_type=?",email,accountType.name());


            if (resultSet.next()){
                Cookie.selectedUser = new User(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        "",
                        accountType
                );
               if (new PasswordConfig().decrypt(password,resultSet.getString("password"))){
                    if(accountType.equals(AccountType.PATIENT)){

                        ResultSet selectedPatientResult = CrudUtil.execute("SELECT patient_id FROM patient WHERE email=?", email);
                        if(selectedPatientResult.next()){
                            setUi("PatientDashboardForm");

                        }else{
                            setUi("PatientRegisterForm");
                        }
//                        setUi("DocterDashboardForm");

                    }else {
                        ResultSet selectedDoctorResult = CrudUtil.execute("SELECT doctor_id FROM doctor WHERE email=?", email);
                        if(selectedDoctorResult.next()){
                            setUi("DocterDashboardForm");
                        }else{
                            setUi("DoctorRegistrationForm");
                        }
                    }
               }
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }


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
