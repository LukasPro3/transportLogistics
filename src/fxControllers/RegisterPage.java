package fxControllers;

import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Driver;
import model.User;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static model.UserRole.DRIVER;
import static model.UserRole.MANAGER;


public class RegisterPage<user> {
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker dateOfBirthField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField password2Field;
    @FXML
    public TextField loginField;
    @FXML
    public Button registerButton;
    @FXML
    public RadioButton managerField;
    @FXML
    public RadioButton driverField;
    public ButtonBar typeOfUser;
    public Label labelRegister;

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private Driver selectedDriver;
    private UserHib userHib;
    //EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("TransportLogistics");
   // UserHib userHib = new UserHib(entityManagerFactory);


    public void setData(EntityManagerFactory entityManagerFactory,User currentUser,Driver selectedDriver) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedDriver=selectedDriver;
        this.userHib= new UserHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Driver driver = (Driver)userHib.getUserById(selectedDriver.getId());
        loginField.setText(driver.getLogin());
        passwordField.setText(driver.getPassword());
        nameField.setText(driver.getFirstName());
        surnameField.setText(driver.getLastName());
        phoneNumberField.setText(driver.getPhoneNumber());
        emailField.setText(driver.getEmail());
        dateOfBirthField.setValue(driver.getBirthDate());
        typeOfUser.setDisable(true);
        registerButton.setOnAction(actionEvent -> {
            updateUser(driver);
        });
        labelRegister.setText("Edit info");
        registerButton.setText("Update");
    }
    public void updateUser(Driver driver){
        driver.setLogin(loginField.getText());
        driver.setPassword(passwordField.getText());
        driver.setLastName(surnameField.getText());
        driver.setFirstName(nameField.getText());
        driver.setBirthDate(dateOfBirthField.getValue());
        driver.setPhoneNumber(phoneNumberField.getText());
        driver.setEmail(emailField.getText());
        userHib.updateUser(driver);
    }

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib= new UserHib(entityManagerFactory);
    }

    public void disableSelect(){
        if(managerField.isSelected()){
            driverField.setDisable(true);
        }
        else if(driverField.isSelected()){
            managerField.setDisable(true);
        }
        else if(!managerField.isSelected()){
            driverField.setDisable(false);
            managerField.setDisable(false);
        }
    }

    public void createNewUser() {
        User user = null;
        if(loginField.getText() == null || passwordField.getText() == null || surnameField.getText()==null|| nameField.getText() == null || dateOfBirthField.getValue() == null || phoneNumberField.getText() == null || emailField.getText() == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("All fields must be filled");
            a.show();
        }
        else if(!passwordField.getText().equals(password2Field.getText())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Passwords do not match :( ");
            a.show();
        }
        else if(managerField.isSelected()) {
            user = new User(nameField.getText(), surnameField.getText(), phoneNumberField.getText(), emailField.getText(), dateOfBirthField.getValue(), loginField.getText(), passwordField.getText(), MANAGER, false);
            userHib.createUser(user);
        }
        else if(driverField.isSelected()){
            user = new Driver(nameField.getText(), surnameField.getText(), phoneNumberField.getText(), emailField.getText(), dateOfBirthField.getValue(), loginField.getText(), passwordField.getText(), DRIVER, false);
            userHib.createUser(user);
        }
        else{
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Select User Type !");
            a.show();
        }

        }
}