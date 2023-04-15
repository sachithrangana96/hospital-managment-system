package com.stack.medex.controller;

import com.jfoenix.controls.*;
import com.stack.medex.util.Cookie;
import com.stack.medex.util.CrudUtil;
import com.stack.medex.util.IdGenerate;
import com.stack.medex.view.tm.DoctorComboView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class NewAppointmentFormController {
    public AnchorPane newAppointmentContext;
    public JFXComboBox<String> cmbDockter;
    public JFXDatePicker txtDate;
    public JFXTimePicker txtTime;
    public JFXTextField txtAmount;
    public JFXTextArea txtMessage;
    String selectedDoctorId = "";
    String selectedPatientId = "";

    private ArrayList<DoctorComboView> viewList = new ArrayList<>();

    public void initialize(){
        setDoctors();
        setPatientData();
    }

    private void setDoctors() {
        try{
            ResultSet set;
            set = CrudUtil.execute("SELECT doctor_id,first_name,last_name FROM doctor");
            ObservableList<String> oblist = FXCollections.observableArrayList();
            int index=1;
            while (set.next()){

                DoctorComboView viewData = new DoctorComboView(
                        index,
                        set.getString(1),
                        set.getString(2) + " " + set.getString(3)
                );

                viewList.add(viewData);
                oblist.add(index+". "+viewData.getName());
               index++;
            }
            cmbDockter.setItems(oblist);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void setPatientData() {
        try {
            ResultSet set = CrudUtil.execute("SELECT patient_id FROM patient WHERE email=?",
                    Cookie.selectedUser.getEmail());
            if (set.next()){

                selectedPatientId  = set.getString(1);
            }else{
                // => redirect patient registration
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PatientDashboardForm");
    }

    public void seeAvailabilityOnAction(ActionEvent actionEvent) {
        Optional<DoctorComboView> selectedRecord = viewList.stream().filter(
                e -> e.getIndex() == Integer.parseInt(cmbDockter.getValue()
                        .split("\\.")[0])).findFirst();
        if(selectedRecord.isPresent()){
//            System.out.println(selectedRecord.get().getDocId());
//            System.out.println(txtDate.getValue());
//            System.out.println(txtTime.getValue());
            selectedDoctorId = selectedRecord.get().getDocId();
            txtAmount.setText(String.valueOf(new Random().nextInt(10000)));
        }else{
            System.out.println("Empty");
        }
    }

    public void submitDateOnAction(ActionEvent actionEvent) {

        String id =  new IdGenerate().generateId(
                "SELECT appointment_id FROM appointment ORDER BY appointment_id DESC LIMIT 1",
                "A"
        );
        try{
         boolean isSaved =   CrudUtil.execute("INSERT INTO appointment VALUES(?,?,?,?,?,?,?)",
                  id,
                  txtDate.getValue(),
                  txtTime.getValue(),
                  Double.parseDouble(txtAmount.getText()),
                    false,
                    selectedPatientId,
                    selectedDoctorId

            );
         if(isSaved){
             new Alert(Alert.AlertType.INFORMATION,"Completed").show();
         }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) newAppointmentContext.getScene().getWindow();
        System.out.println(stage);
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
