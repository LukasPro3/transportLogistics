package fxControllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.CargoStatus;

public class CargoTableParameters {
    private SimpleStringProperty cargoId = new SimpleStringProperty();
    private SimpleStringProperty cargoAmount = new SimpleStringProperty();
    private SimpleStringProperty cargoPrice = new SimpleStringProperty();
    private SimpleStringProperty cargoCollection = new SimpleStringProperty();
    private SimpleStringProperty cargoDelivery = new SimpleStringProperty();
    private SimpleStringProperty cargoStatus = new SimpleStringProperty();

    public CargoTableParameters(SimpleStringProperty cargoId, SimpleStringProperty cargoAmount, SimpleStringProperty cargoPrice, SimpleStringProperty cargoCollection, SimpleStringProperty cargoDelivery, SimpleStringProperty cargoStatus) {
        this.cargoId = cargoId;
        this.cargoAmount = cargoAmount;
        this.cargoPrice = cargoPrice;
        this.cargoCollection = cargoCollection;
        this.cargoDelivery = cargoDelivery;
        this.cargoStatus = cargoStatus;
    }

    public CargoTableParameters() {
    }

    public String getCargoId() {
        return cargoId.get();
    }

    public SimpleStringProperty cargoIdProperty() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId.set(cargoId);
    }

    public String getCargoAmount() {
        return cargoAmount.get();
    }

    public SimpleStringProperty cargoAmountProperty() {
        return cargoAmount;
    }

    public void setCargoAmount(String cargoAmount) {
        this.cargoAmount.set(cargoAmount);
    }

    public String getCargoPrice() {
        return cargoPrice.get();
    }

    public SimpleStringProperty cargoPriceProperty() {
        return cargoPrice;
    }

    public void setCargoPrice(String cargoPrice) {
        this.cargoPrice.set(cargoPrice);
    }

    public String getCargoCollection() {
        return cargoCollection.get();
    }

    public SimpleStringProperty cargoCollectionProperty() {
        return cargoCollection;
    }

    public void setCargoCollection(String cargoCollection) {
        this.cargoCollection.set(cargoCollection);
    }

    public String getCargoDelivery() {
        return cargoDelivery.get();
    }

    public SimpleStringProperty cargoDeliveryProperty() {
        return cargoDelivery;
    }

    public void setCargoDelivery(String cargoDelivery) {
        this.cargoDelivery.set(cargoDelivery);
    }

    public String getCargoStatus() {
        return cargoStatus.get();
    }

    public SimpleStringProperty cargoStatusProperty() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus.set(cargoStatus);
    }
}
