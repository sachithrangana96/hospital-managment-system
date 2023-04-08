package com.stack.medex.controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.stack.medex.db.Database;
import com.stack.medex.dto.DocktorDto;
import com.stack.medex.dto.User;
import com.stack.medex.enums.GenderType;
import com.stack.medex.util.Cookie;
import com.stack.medex.util.CrudUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    private String generateDocterId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT doctor_id FROM doctor ORDER BY doctor_id DESC LIMIT 1"); // if the primary key is a string don't use this method
        // unsigned, cast, subscribe
        if(result.next()){
            String selectedId = result.getString(1); // D-1
            String[] splitData = selectedId.split("-");// string tokenizer, String format
            String splitId = splitData[1];
            int id = Integer.parseInt(splitId);// unboxing
            id++;
            return "D-"+id;
        }
        return "D-1";
    }

    public void submitDataOnAction(ActionEvent actionEvent) {

        try{
            String docId = generateDocterId();
          boolean isSaved =  CrudUtil.execute("INSERT INTO doctor VALUES(?,?,?,?,?,?,?,?)",
                    docId,
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtContact.getText(),
                    txtEmail.getText(),
                    txtSpecillization.getText(),
                    txtAddress.getText(),
                    rbtnMale.isSelected() ? GenderType.MALE.name() : GenderType.FEMALE.name()
                    );
          if(isSaved){
              new Alert(Alert.AlertType.INFORMATION,"Welcome Doctor").show();
              setUi("DocterDashboardForm");
          }
        }catch (SQLException | ClassNotFoundException | IOException e){
            e.printStackTrace();
        }


//        if(Database.doctorTable.stream().filter(e->e.getNic().equals(txtNic.getText().trim())).findFirst().isPresent()){
//            new Alert(Alert.AlertType.WARNING,"Nic Conflict").show();
//            btnSubmit.setDisable(true);
//            txtNic.setStyle("-fx-border-color: red");
//            return;
//        }

//        DocktorDto docktorDto = new DocktorDto(
//                txtFirstName.getText(),
//                txtLastName.getText(),
//                txtNic.getText(),
//                txtContact.getText(),
//                txtEmail.getText(),
//                txtSpecillization.getText(),
//                txtAddress.getText(),
//                rbtnMale.isSelected()? GenderType.MALE:GenderType.FEMALE
//
//        );
//        Database.doctorTable.add(docktorDto);
//        Stage stage = (Stage) doctorRegistrationContext.getScene().getWindow();
//        stage.close();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) doctorRegistrationContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

}
