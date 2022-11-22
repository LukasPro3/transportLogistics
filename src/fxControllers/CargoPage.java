package fxControllers;

import hibernate.CargoHib;
import hibernate.TripHib;
import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Cargo;
import model.CargoStatus;
import model.Trip;

import javax.persistence.EntityManagerFactory;
import java.time.LocalTime;

public class CargoPage {
    public TextField priceField;
    public TextField weightField;
    public TextField amountField;
    public TextField heightField;
    public TextField lengthField;
    public TextField widthField;
    public TextField collectionField;
    public TextField deliveryField;
    public TextField otherInfoField;
    public TextField crmField;
    public Button createButton;

    private CargoHib cargoHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.cargoHib = new CargoHib(entityManagerFactory);
    }

    public void createCargo() {
        Cargo cargo = null;
        cargo = new Cargo(Double.parseDouble(priceField.getText()),Double.parseDouble(amountField.getText()),Double.parseDouble(weightField.getText()),Double.parseDouble(heightField.getText()),Double.parseDouble(widthField.getText()),Double.parseDouble(lengthField.getText()),collectionField.getText(),deliveryField.getText(),otherInfoField.getText(),crmField.getText(), CargoStatus.READY_FOR_PICK_UP,null,null);
        cargoHib.createCargo(cargo);
    }
}
