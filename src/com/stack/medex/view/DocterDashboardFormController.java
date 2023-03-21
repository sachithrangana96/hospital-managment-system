package com.stack.medex.view;

import com.stack.medex.util.Cookie;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DocterDashboardFormController {
    public AnchorPane docterDashboardContext;

    public void initializer() throws IOException {
        checkUser();
    }

    public void checkUser() throws IOException {
        if(null== Cookie.selectedUser){
            Stage stage = (Stage) docterDashboardContext.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
            stage.centerOnScreen();
        }
    }
}
